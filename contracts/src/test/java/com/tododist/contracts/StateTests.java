package com.tododist.contracts;

import com.tododist.states.ToDoState;
import org.junit.Test;

public class StateTests {

    //Mock State test check for if the state has correct parameters type
    @Test
    public void hasFieldOfCorrectType() throws NoSuchFieldException {
        ToDoState.class.getDeclaredField("msg");
        assert (ToDoState.class.getDeclaredField("msg").getType().equals(String.class));
    }
}