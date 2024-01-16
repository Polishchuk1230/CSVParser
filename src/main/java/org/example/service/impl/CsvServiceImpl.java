package org.example.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.example.exception.ColumnNotFountException;
import org.example.exception.FileIsEmptyException;
import org.example.exception.TableIsEmptyException;
import org.example.service.CsvService;

public class CsvServiceImpl implements CsvService, AutoCloseable {
  private static final String RESPONSE_VALUE_SEPARATOR = " ";
  private static final String COMMA = ",";

  private final BufferedReader bufferedReader;

  public CsvServiceImpl(BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
  }

  @Override
  public List<String> count(String columnName) throws IOException {
    String[] headerNames = fetchFirstLineOrThrowException(bufferedReader).split(COMMA);
    int columnPosition = findColumnPositionOrThrowException(columnName, headerNames);

    Map<String, Long> map = bufferedReader.lines()
        .map(line -> line.split(COMMA))
        .map(arr -> arr[columnPosition])
        .filter(value -> !value.equalsIgnoreCase(columnName))
        .collect(
            Collectors.groupingBy(str -> str, Collectors.counting()));

    return collectResponseForCount(map, columnName);
  }

  private List<String> collectResponseForCount(Map<String, Long> contentMap, String columnName) {
    Comparator<Map.Entry<String, Long>> compByValue = Comparator.comparingLong(Entry::getValue);
    List<String> result = contentMap.entrySet().stream()
        .sorted(compByValue.reversed())
        .map(entry -> entry.getKey() + RESPONSE_VALUE_SEPARATOR + entry.getValue())
        .collect(Collectors.toList());
    result.add(0, String.format(columnName + RESPONSE_VALUE_SEPARATOR + "Count"));
    return result;
  }

  @Override
  public List<String> findMax(String columnName) throws IOException {
    String[] headerNames = fetchFirstLineOrThrowException(bufferedReader).split(COMMA);
    int columnPosition = findColumnPositionOrThrowException(columnName, headerNames);

    String[] maxValueArr = bufferedReader.lines()
        .map(str -> str.split(COMMA))
        .reduce(null, (resultArr, splitLine) -> {
          if (resultArr == null) {
            return new String[] {splitLine[0], splitLine[columnPosition]};
          }
          int oldValue = Integer.parseInt(resultArr[1]);
          int newValue = Integer.parseInt(splitLine[columnPosition]);
          if (newValue > oldValue) {
            return new String[] {splitLine[0], splitLine[columnPosition]};
          }
          return resultArr;
        });

    if (maxValueArr == null) {
      throw new TableIsEmptyException("Table doesn't contain any data");
    }
    return collectResponseForFindMax(columnPosition, headerNames, maxValueArr);
  }

  private List<String> collectResponseForFindMax(int columnPosition, String[] headerNames, String[] maxValue) {
    List<String> result = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    sb.append(headerNames[0]).append(RESPONSE_VALUE_SEPARATOR).append(headerNames[columnPosition]);
    result.add(sb.toString());

    sb = new StringBuilder();
    sb.append(maxValue[0]).append(RESPONSE_VALUE_SEPARATOR).append(maxValue[1]);
    result.add(sb.toString());
    return result;
  }

  @Override
  public void close() throws Exception {
    this.bufferedReader.close();
  }

  private String fetchFirstLineOrThrowException(BufferedReader reader) throws IOException {
    String firstLine = reader.readLine();
    if (firstLine == null) {
      throw new FileIsEmptyException("File is empty");
    }
    return firstLine;
  }

  private int findColumnPositionOrThrowException(String columnName, String[] headerNames) {
    int columnPosition = -1;
    for (int i = 0; i < headerNames.length; i++) {
      if (headerNames[i].equalsIgnoreCase(columnName)) {
        columnPosition = i;
        break;
      }
    }
    if (columnPosition < 0) {
      throw new ColumnNotFountException(
          String.format("The column \"%s\" was not found", columnName));
    }
    return columnPosition;
  }
}
