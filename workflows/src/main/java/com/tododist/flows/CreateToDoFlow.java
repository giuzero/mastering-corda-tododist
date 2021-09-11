package com.tododist.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.tododist.contracts.DummyCommand;
import com.tododist.states.ToDoState;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.node.ServiceHub;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Collections;


@StartableByRPC
    public class CreateToDoFlow extends FlowLogic<String>{

        private final String taskDescription;

        //public constructor
        public CreateToDoFlow(String task) {
            this.taskDescription = task;
        }

        @Suspendable
        @Override
        public String call() throws FlowException{
            //to get a reference to look up the notary (I'm creating a state, there is no double spending, I dont really need the notary...)
            ServiceHub serviceHub = getServiceHub();
            Party notary = serviceHub.getNetworkMapCache().getNotaryIdentities().get(0);
            Party me = getOurIdentity();
            ToDoState ts = new ToDoState(me,me,taskDescription);
            TransactionBuilder tb = new TransactionBuilder(notary);
            tb.addOutputState(ts);
            tb = tb.addCommand(new DummyCommand(), me.getOwningKey());
            //Finality is not required since we do not need notary to sign and assigner = assignee
            SignedTransaction stx = getServiceHub().signInitialTransaction(tb);
            subFlow(new FinalityFlow(stx, Collections.<FlowSession>emptySet()));
            //System.out.println(ts.getLinearId().toString());
            return ts.getLinearId().toString();
        }


    }




