package org.example.validator;

import java.util.Arrays;
import org.example.model.Action;
import org.example.service.impl.InputHandlerImpl;

import static org.example.App.APP_NAME;

public class InputValidator {
  public static final int COMMAND_PARTS_COUNT = 4;

  public boolean isValid(String input) {
    String[] splitInput = input.split(" ");
    return input.startsWith(APP_NAME) &&
        splitInput.length == COMMAND_PARTS_COUNT &&
        Arrays.stream(Action.values()).anyMatch(
            action -> action.toString().equalsIgnoreCase(splitInput[InputHandlerImpl.COMMAND_PART_ACTION]));
  }
}
