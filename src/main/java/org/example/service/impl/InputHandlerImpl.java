package org.example.service.impl;

import static org.example.exception.ExceptionMessage.WRONG_INPUT_MESSAGE;

import java.util.List;
import org.example.service.ExceptionHandler;
import org.example.service.InputHandler;
import org.example.validator.InputValidator;

public class InputHandlerImpl implements InputHandler {
  public static final String USER_INPUT_VALUE_SEPARATOR = " "; // space
  public static final int FILE_NAME_POSITION_NUMBER = 1;
  public static final int ACTION_TYPE_POSITION_NUMBER = 2;
  public static final int ACTION_PARAMETER_POSITION_NUMBER = 3;

  private final ExceptionHandler exceptionHandler;
  private final InputValidator inputValidator;

  public InputHandlerImpl(ExceptionHandler exceptionHandler, InputValidator inputValidator) {
    this.exceptionHandler = exceptionHandler;
    this.inputValidator = inputValidator;
  }

  @Override
  public List<String> processInput(String input) {
    if (!inputValidator.isValid(input)) {
      return List.of(WRONG_INPUT_MESSAGE);
    }
    String[] splitInput = input.split(USER_INPUT_VALUE_SEPARATOR);

    String pathToFile = splitInput[FILE_NAME_POSITION_NUMBER];
    String actionName = splitInput[ACTION_TYPE_POSITION_NUMBER];
    String parameter = splitInput[ACTION_PARAMETER_POSITION_NUMBER];

    List<String> processedData;
    try (CsvProcessorServiceImpl csvProcessorService = new CsvProcessorServiceImpl(
        new UploadServiceImpl().uploadFile(pathToFile))) {
      processedData = new ActionDispatcherImpl(csvProcessorService)
          .dispatchAction(actionName, parameter);
    } catch(Exception e) {
      processedData = exceptionHandler.handleException(e);
    }
    return processedData;
  }
}
