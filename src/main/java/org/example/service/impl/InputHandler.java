package org.example.service.impl;

import static org.example.exception.ExceptionMessage.FILE_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.example.exception.ExceptionMessage.NUMBER_FORMAT_EXCEPTION_MESSAGE;
import static org.example.exception.ExceptionMessage.UNEXPECTED_EXCEPTION_MESSAGE_PATTERN;
import static org.example.exception.ExceptionMessage.WRONG_INPUT_MESSAGE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.example.exception.ColumnNotFountException;
import org.example.exception.FileIsEmptyException;
import org.example.exception.TableIsEmptyException;
import org.example.service.IInputHandler;
import org.example.service.IUploadService;
import org.example.validator.InputValidator;

public class InputHandler implements IInputHandler {
  public static final int FILE_NAME_POSITION_NUMBER = 0;
  public static final int ACTION_TYPE_POSITION_NUMBER = 1;
  public static final int ACTION_PARAMETERS_POSITION_NUMBER = 2;

  private final InputValidator inputValidator;
  private final IUploadService<BufferedReader> uploadService;

  public InputHandler(InputValidator inputValidator, IUploadService<BufferedReader> uploadService) {
    this.inputValidator = inputValidator;
    this.uploadService = uploadService;
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
    try (ActionDispatcher actionDispatcher = new ActionDispatcher(uploadService.uploadFile(pathToFile))) {
      processedData = actionDispatcher.dispatchAction(actionName, parameters);
    } catch (ColumnNotFountException | FileIsEmptyException | TableIsEmptyException exception) {
      processedData = List.of(exception.getMessage());
    } catch (FileNotFoundException exception) {
      processedData = List.of(FILE_NOT_FOUND_EXCEPTION_MESSAGE);
    } catch (NumberFormatException exception) {
      processedData = List.of(NUMBER_FORMAT_EXCEPTION_MESSAGE);
    } catch(Exception exception) {
      exception.printStackTrace();
      processedData = List.of(
          String.format(UNEXPECTED_EXCEPTION_MESSAGE_PATTERN, exception.getClass().getSimpleName()));
    }
    return processedData;
  }
}
