package org.example.service.impl;

import static org.example.exception.ExceptionMessage.WRONG_INPUT_MESSAGE;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import org.example.dto.FileContentDto;
import org.example.service.IExceptionHandler;
import org.example.service.IUploadService;
import org.example.validator.InputValidator;

public class InputHandler implements org.example.service.IInputHandler {
  public static final String USER_INPUT_VALUE_SEPARATOR = " "; // space
  public static final int FILE_NAME_POSITION_NUMBER = 1;
  public static final int ACTION_TYPE_POSITION_NUMBER = 2;
  public static final int ACTION_PARAMETERS_POSITION_NUMBER = 3;

  private final IExceptionHandler IExceptionHandler;
  private final InputValidator inputValidator;
  private final IUploadService<BufferedReader> IUploadService;

  public InputHandler(IExceptionHandler IExceptionHandler, InputValidator inputValidator,
      IUploadService<BufferedReader> IUploadService) {
    this.IExceptionHandler = IExceptionHandler;
    this.inputValidator = inputValidator;
    this.IUploadService = IUploadService;
  }

  @Override
  public List<String> processInput(String input) {
    if (!inputValidator.isValid(input)) {
      return List.of(WRONG_INPUT_MESSAGE);
    }
    String[] splitInput = input.split(USER_INPUT_VALUE_SEPARATOR);

    String pathToFile = splitInput[FILE_NAME_POSITION_NUMBER];
    String actionName = splitInput[ACTION_TYPE_POSITION_NUMBER];
    String[] parameters = Arrays.copyOfRange(splitInput, ACTION_PARAMETERS_POSITION_NUMBER, splitInput.length);

    List<String> processedData;
    try {
      FileContentDto<BufferedReader> fileContentDto = IUploadService.uploadFile(pathToFile);
      processedData = new ActionDispatcher(fileContentDto).dispatchAction(actionName, parameters);
    } catch(Exception e) {
      processedData = IExceptionHandler.handleException(e);
    }
    return processedData;
  }
}
