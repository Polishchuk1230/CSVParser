package org.example.exception;

public final class ExceptionMessage {
  private ExceptionMessage() {
    throw new UnsupportedOperationException("ExceptionMessage class isn't supposed to be instantiated.");
  }

  public static final String WRONG_INPUT_MESSAGE =
      "Wrong input format.\nRight format: myparser <file_name.csv> find_max|count <column_name>";

  public static final String NUMBER_FORMAT_EXCEPTION_MESSAGE =
      "There was an action applied to a wrong column.\n" +
          "find_max can be applied only to columns with numeric values.";

  public static final String FILE_NOT_FOUND_EXCEPTION_MESSAGE = "Your file is absent.";

  public static final String FILE_IS_EMPTY_EXCEPTION_MESSAGE = "File is empty";

  public static final String TABLE_IS_EMPTY_EXCEPTION_MESSAGE = "Table doesn't contain any data";

  public static final String COLUMN_NOT_FOUND_EXCEPTION_MESSAGE_PATTERN = "The column \"%s\" was not found";

  public static final String UNEXPECTED_EXCEPTION_MESSAGE_PATTERN = "Unexpected exception: %s";
}
