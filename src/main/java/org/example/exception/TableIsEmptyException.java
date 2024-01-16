package org.example.exception;

public class TableIsEmptyException extends RuntimeException {

  public TableIsEmptyException(String message) {
    super(message);
  }
}
