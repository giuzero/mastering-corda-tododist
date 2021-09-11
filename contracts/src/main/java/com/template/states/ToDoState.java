package com.template.states;

import com.template.contracts.TemplateContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.List;

// *********
// * State *
// *********
@BelongsToContract(TemplateContract.class)
public class ToDoState implements ContractState, LinearState {

    //private variables
    private final UniqueIdentifier linearId = new UniqueIdentifier();
    private final String taskDecription;
    private final Party assignedBy;
    private final Party assignedTo;

    /* Constructor of your Corda state */
    public ToDoState(Party assignedBy, Party assignedTo, String taskDecription) {
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.taskDecription = taskDecription;
    }

    //getters
    public Party getAssignedBy() { return assignedBy; }
    public Party getAssignedTo() { return assignedTo; }
    public String getTaskDescriprion() { return taskDecription; }

    /*
    need these method for a linear state
     */
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(assignedBy,assignedTo);
    }

    @Override
    public UniqueIdentifier getLinearId(){
        return linearId;
    }
}