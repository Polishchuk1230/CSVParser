package org.example.service;

import java.util.List;

public class ExceptionHandlerImpl implements ExceptionHandler {

  @Override
  public List<String> handleException(Exception exception) {
    return switch (exception.getClass().getSimpleName()) {
      case "NumberFormatException" -> List.of("There was an action applied to a wrong column.\nfind_max can be applied only to columns with numeric values.");
      case "FileNotFoundException" -> List.of("Wrong path to your csv file.");
      default -> {
        exception.printStackTrace();
        yield List.of("Not expected exception: " + exception.getClass().getSimpleName());
      }
    };
  }
}
