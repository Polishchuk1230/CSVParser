package org.example.service.impl;

import static org.example.exception.ExceptionMessage.FILE_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.example.exception.ExceptionMessage.NUMBER_FORMAT_EXCEPTION_MESSAGE;
import static org.example.exception.ExceptionMessage.UNEXPECTED_EXCEPTION_MESSAGE_PATTERN;

import java.util.List;
import org.example.service.ExceptionHandler;

public class ExceptionHandlerImpl implements ExceptionHandler {

  @Override
  public List<String> handleException(Exception exception) {
    return switch (exception.getClass().getSimpleName()) {
      case "FileIsEmptyException",
          "TableIsEmptyException",
          "ColumnNotFountException" -> List.of(exception.getMessage());
      case "NumberFormatException" -> List.of(NUMBER_FORMAT_EXCEPTION_MESSAGE);
      case "FileNotFoundException" -> List.of(FILE_NOT_FOUND_EXCEPTION_MESSAGE);
      default -> {
        exception.printStackTrace();
        yield List.of(
            String.format(UNEXPECTED_EXCEPTION_MESSAGE_PATTERN, exception.getClass().getSimpleName()));
      }
    };
  }
}
