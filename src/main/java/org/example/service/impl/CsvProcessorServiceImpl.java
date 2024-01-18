package org.example.service.impl;

import static org.example.exception.ExceptionMessage.COLUMN_NOT_FOUND_EXCEPTION_MESSAGE_PATTERN;
import static org.example.exception.ExceptionMessage.FILE_IS_EMPTY_EXCEPTION_MESSAGE;
import static org.example.exception.ExceptionMessage.TABLE_IS_EMPTY_EXCEPTION_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.example.dto.FileContentDto;
import org.example.exception.ColumnNotFountException;
import org.example.exception.FileIsEmptyException;
import org.example.exception.TableIsEmptyException;
import org.example.service.CsvProcessorService;

public class CsvProcessorServiceImpl implements CsvProcessorService, AutoCloseable {
  private static final String RESPONSE_VALUE_SEPARATOR = " ";
  private static final String COMMA = ",";

  private final BufferedReader bufferedReader;

  public CsvProcessorServiceImpl(FileContentDto<BufferedReader> fileContentDto) {
    this.bufferedReader = fileContentDto.getFileContent();
  }

  @Override
  public List<String> count(String columnName) {
    String[] headerNames = fetchFirstLine(bufferedReader).split(COMMA);
    int columnPosition = findColumnPosition(columnName, headerNames);

    Map<String, Long> map = bufferedReader.lines()
        .map(line -> line.split(COMMA))
        .map(arr -> arr[columnPosition])
        .collect(
            Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return collectResponseForCount(map, columnName);
  }

  private List<String> collectResponseForCount(Map<String, Long> contentMap, String columnName) {
    Comparator<Map.Entry<String, Long>> compByValue = Comparator.comparingLong(Entry::getValue);
    List<String> result = contentMap.entrySet().stream()
        .sorted(compByValue.reversed())
        .map(entry -> entry.getKey() + RESPONSE_VALUE_SEPARATOR + entry.getValue())
        .collect(Collectors.toList());
    result.add(0, columnName + RESPONSE_VALUE_SEPARATOR + "Count");
    return result;
  }

  @Override
  public List<String> findMax(String columnName) {
    String[] headerNames = fetchFirstLine(bufferedReader).split(COMMA);
    int columnPosition = findColumnPosition(columnName, headerNames);

    String[] maxValueArr = bufferedReader.lines()
        .map(str -> str.split(COMMA))
        .reduce(null, (resultArr, splitLine) ->
            (resultArr == null || Integer.parseInt(splitLine[columnPosition]) > Integer.parseInt(resultArr[1])) ?
                new String[] {splitLine[0], splitLine[columnPosition]} :
                resultArr);

    if (maxValueArr == null) {
      throw new TableIsEmptyException(TABLE_IS_EMPTY_EXCEPTION_MESSAGE);
    }
    return collectResponseForFindMax(columnPosition, headerNames, maxValueArr);
  }

  private List<String> collectResponseForFindMax(int columnPosition, String[] headerNames, String[] maxValue) {
    List<String> result = new ArrayList<>();
    result.add(headerNames[0] + RESPONSE_VALUE_SEPARATOR + headerNames[columnPosition]);
    result.add(maxValue[0] + RESPONSE_VALUE_SEPARATOR + maxValue[1]);
    return result;
  }

  private String fetchFirstLine(BufferedReader reader) {
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

  private int findColumnPosition(String columnName, String[] headerNames) {
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
