package com.tododist.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;
import org.jetbrains.annotations.NotNull;

@InitiatedBy(AssignToDoInitiatorFlow.class)
public class AssignToDoResponderFlow extends FlowLogic<SignedTransaction> {
    private FlowSession counterPartySession;

    public AssignToDoResponderFlow(FlowSession counterPartySession){
        this.counterPartySession=counterPartySession;
    }

    @Override
    @Suspendable
    public SignedTransaction call() throws FlowException{
        final SignTransactionFlow signTransactionFlow = new SignTransactionFlow(counterPartySession) {
            @Override
            protected void checkTransaction(@NotNull SignedTransaction stx) throws FlowException {
                System.out.println("checked!!");
            }
        };
        SignedTransaction stx = subFlow(signTransactionFlow);
        return subFlow(new ReceiveFinalityFlow(counterPartySession, stx.getId()));
    }

}