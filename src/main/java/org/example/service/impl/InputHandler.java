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
  public static final int FILE_NAME_POSITION_NUMBER = 0;
  public static final int ACTION_TYPE_POSITION_NUMBER = 1;
  public static final int ACTION_PARAMETERS_POSITION_NUMBER = 2;

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
  public List<String> processInput(String[] args) {
    if (!inputValidator.isValid(args)) {
      return List.of(WRONG_INPUT_MESSAGE);
    }

    String pathToFile = args[FILE_NAME_POSITION_NUMBER];
    String actionName = args[ACTION_TYPE_POSITION_NUMBER];
    String[] parameters = Arrays.copyOfRange(args, ACTION_PARAMETERS_POSITION_NUMBER, args.length);

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
