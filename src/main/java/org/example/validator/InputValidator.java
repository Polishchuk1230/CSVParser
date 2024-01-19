package org.example.validator;

import static org.example.service.impl.InputHandler.ACTION_TYPE_POSITION_NUMBER;

import java.util.Arrays;
import org.example.model.Action;

public class InputValidator {
  public static final int COMMAND_PARTS_MINIMAL_COUNT = 3;

  public boolean isValid(String[] args) {
    return args.length >= COMMAND_PARTS_MINIMAL_COUNT &&
        Arrays.stream(Action.values()).anyMatch(
            action -> action.toString().equalsIgnoreCase(args[ACTION_TYPE_POSITION_NUMBER]));
  }
}
