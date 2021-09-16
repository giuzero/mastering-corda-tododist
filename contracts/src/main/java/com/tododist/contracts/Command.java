package com.tododist.contracts;

import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;

public interface Command  {
    public class CreateToDoCommand implements CommandData{}
    public class AssignToDoCommand implements CommandData{}
    public class MarkCompleteToDoCommand implements CommandData{}
}
