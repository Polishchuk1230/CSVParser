package org.example.service.impl;

import java.util.List;
import org.example.service.ExceptionHandler;

public class ExceptionHandlerImpl implements ExceptionHandler {

  @Override
  public List<String> handleException(Exception exception) {
    return switch (exception.getClass().getSimpleName()) {
      case "TableIsEmptyException", "FileIsEmptyException", "ColumnNotFountException" ->
          List.of(exception.getMessage());
      case "NumberFormatException" ->
          List.of("There was an action applied to a wrong column.",
              "find_max can be applied only to columns with numeric values.");
      case "FileNotFoundException" -> List.of("Wrong path to your csv file.");
      default -> {
        exception.printStackTrace();
        yield List.of("Not expected exception: " + exception.getClass().getSimpleName());
      }
    };
  }
}
