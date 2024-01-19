package org.example.validator;

import static org.example.service.impl.InputHandler.ACTION_TYPE_POSITION_NUMBER;

import java.util.Arrays;
import org.example.model.Action;

import static org.example.App.APP_NAME;

public class InputValidator {
  public static final int COMMAND_PARTS_MINIMAL_COUNT = 4;

  public boolean isValid(String input) {
    String[] splitInput = input.split(" ");
    return input.startsWith(APP_NAME) &&
        splitInput.length >= COMMAND_PARTS_MINIMAL_COUNT &&
        Arrays.stream(Action.values()).anyMatch(
            action -> action.toString().equalsIgnoreCase(splitInput[ACTION_TYPE_POSITION_NUMBER]));
  }
}
