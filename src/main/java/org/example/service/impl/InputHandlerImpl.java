package org.example.service.impl;

import java.util.List;
import org.example.service.ExceptionHandler;
import org.example.service.InputHandler;
import org.example.validator.InputValidator;

public class InputHandlerImpl implements InputHandler {
  public static final String USER_INPUT_VALUE_SEPARATOR = " "; // space
  public static final String WRONG_INPUT_MESSAGE =
      "Wrong format.\nRight format: myparser <file_name.csv> find_max|count <column_name>";
  public static final int COMMAND_PART_FILE_NAME = 1;
  public static final int COMMAND_PART_ACTION = 2;
  public static final int COMMAND_PART_PARAMETER = 3;

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

    String pathToFile = splitInput[COMMAND_PART_FILE_NAME];
    String actionName = splitInput[COMMAND_PART_ACTION];
    String parameter = splitInput[COMMAND_PART_PARAMETER];

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
