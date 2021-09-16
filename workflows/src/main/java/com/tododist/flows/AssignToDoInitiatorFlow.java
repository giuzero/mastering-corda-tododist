package com.tododist.flows;


import co.paralleluniverse.fibers.Suspendable;
import com.tododist.contracts.Command;
import com.tododist.contracts.DummyCommand;
import com.tododist.states.ToDoState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.identity.Party;
import net.corda.core.node.ServiceHub;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;

import java.security.PublicKey;
import java.util.*;

@InitiatingFlow
@StartableByRPC
public class AssignToDoInitiatorFlow extends FlowLogic<String> {
    private String linearId;
    private String assignedTo;

    public AssignToDoInitiatorFlow(String linearId, String assignedTo){
        this.linearId = linearId;
        this.assignedTo = assignedTo;
    }

    @Suspendable
    @Override
    public String call() throws FlowException{
        ServiceHub sb = getServiceHub();

        QueryCriteria q = new QueryCriteria.LinearStateQueryCriteria(
                null, ImmutableList.of(UUID.fromString(linearId)));
        //Issue the query to the Vault
        Vault.Page<ToDoState> taskStatePage = sb.getVaultService().queryBy(ToDoState.class, q);
        //unpacking
        List<StateAndRef<ToDoState>> states = taskStatePage.getStates();
        StateAndRef<ToDoState> currentStateAndRefToDo = states.get(0);
        ToDoState toDoState = currentStateAndRefToDo.getState().getData();
        Set<Party> parties = sb.getIdentityService().partiesFromName(assignedTo, true);
        Party assignedToParty = parties.iterator().next(); //want the next Party
        ToDoState newToDoState = toDoState.assign(assignedToParty);

        //get public keys to sign transactions
        PublicKey myKey = getOurIdentity().getOwningKey();
        PublicKey counterPartyKey = assignedToParty.getOwningKey();

        //Get the notary to build transaction
        Party notary = sb.getNetworkMapCache().getNotaryIdentities().get(0);
        TransactionBuilder tb = new TransactionBuilder(notary).addInputState(currentStateAndRefToDo)
                .addOutputState(newToDoState).addCommand(new Command.AssignToDoCommand(), myKey, counterPartyKey);
        SignedTransaction ptx = getServiceHub().signInitialTransaction(tb);

        //transaction is ready to be signed by parties --> use FlowSession
        FlowSession assignedToSession = initiateFlow(assignedToParty);
        //try{
            SignedTransaction stx = subFlow(new CollectSignaturesFlow(ptx, ImmutableSet.of(assignedToSession)));
            subFlow(new FinalityFlow(stx, Arrays.asList(assignedToSession)));
        //} catch(FlowException e){
        //    System.out.println("TRANSACTION FINALITY FAIL");
        //}

        return toDoState.getTaskDescription();
    }
}
