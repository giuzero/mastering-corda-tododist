package com.tododist.contracts;

import com.tododist.states.ToDoState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

public class ToDoContract implements Contract {

    @Override
    public void verify(LedgerTransaction tx){
        System.out.println("CONTRACT CALLED");
        //retrieve command
        final List<CommandWithParties<CommandData>> commands = tx.getCommands();
        final CommandData command = commands.get(0).getValue();

        ToDoState toDoOutputState = (ToDoState)tx.getOutputStates().get(0);

        if(command instanceof Command.CreateToDoCommand){
            requireThat(r ->{
            r.using("Blank task", !toDoOutputState.getTaskDescription().trim().equals(""));
            r.using("Task too long (MAX30CHARS)", !(toDoOutputState.getTaskDescription().length() > 30));
        return null;});
        }
        else if(command instanceof Command.AssignToDoCommand){
            ToDoState toDoInputState = (ToDoState)tx.getInputStates().get(0);
            requireThat(r ->{
                r.using("Already assigned to this party",
                        !toDoInputState.getAssignedTo().equals(toDoOutputState.getAssignedTo()));
                return null;});
        }

    }
}
