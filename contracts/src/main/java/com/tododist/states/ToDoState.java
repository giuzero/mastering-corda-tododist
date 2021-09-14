package com.tododist.states;

import org.jetbrains.annotations.NotNull;
import com.tododist.contracts.TemplateContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.serialization.ConstructorForDeserialization;

import java.util.Arrays;
import java.util.List;

@BelongsToContract(TemplateContract.class)
public class ToDoState implements LinearState, ContractState {

    //private variables
    private final UniqueIdentifier linearId; //I MUST NOT GIVE VALUE NOW!
    private final String taskDescription;
    private final Party assignedBy;
    private final Party assignedTo;

    /* Constructor of your Corda state */
    public ToDoState(Party assignedBy, Party assignedTo, String taskDescription) {
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.taskDescription = taskDescription;
        this.linearId = new UniqueIdentifier();
    }

    //Constructor for keeping the linearId
    @ConstructorForDeserialization
    public ToDoState(Party assignedBy, Party assignedTo, String taskDescription, UniqueIdentifier linearId){
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.taskDescription = taskDescription;
        this.linearId = linearId;
    }

    public ToDoState assign(Party assignedTo){
        return new ToDoState(assignedBy, assignedTo, taskDescription, linearId);
    }

    //getters
    public Party getAssignedBy() { return assignedBy; }
    public Party getAssignedTo() { return assignedTo; }
    public String getTaskDescription() { return taskDescription; }

    /*
    need these method for a linear state
     */
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(assignedBy,assignedTo);
    }

    @Override
    @NotNull
    public UniqueIdentifier getLinearId(){
        return linearId;
    }
}