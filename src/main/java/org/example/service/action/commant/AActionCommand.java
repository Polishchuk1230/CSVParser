package org.example.service.action.commant;

import static org.example.exception.ExceptionMessage.COLUMN_NOT_FOUND_EXCEPTION_MESSAGE_PATTERN;
import static org.example.exception.ExceptionMessage.FILE_IS_EMPTY_EXCEPTION_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import org.example.dto.FileContentDto;
import org.example.exception.ColumnNotFountException;
import org.example.exception.FileIsEmptyException;

public abstract class AActionCommand implements AutoCloseable {

  protected static final String RESPONSE_VALUE_SEPARATOR = " ";
  protected static final String COMMA = ",";

  protected final BufferedReader bufferedReader;

  public abstract List<String> execute(String columnName);

  public AActionCommand(FileContentDto<BufferedReader> fileContentDto) {
    this.bufferedReader = fileContentDto.getFileContent();
  }

  protected String fetchFirstLine(BufferedReader reader) {
    try {
      String firstLine = reader.readLine();
      if (firstLine == null) {
        throw new FileIsEmptyException(FILE_IS_EMPTY_EXCEPTION_MESSAGE);
      }
      return firstLine;
    } catch(IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  protected int findColumnPosition(String columnName, String[] headerNames) {
    int columnPosition = -1;
    for (int i = 0; i < headerNames.length; i++) {
      if (headerNames[i].equalsIgnoreCase(columnName)) {
        columnPosition = i;
        break;
      }
    }
    if (columnPosition < 0) {
      throw new ColumnNotFountException(
          String.format(COLUMN_NOT_FOUND_EXCEPTION_MESSAGE_PATTERN, columnName));
    }
    return columnPosition;
  }

  @Override
  public void close() {
    try {
      bufferedReader.close();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
