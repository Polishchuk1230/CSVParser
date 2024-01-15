package org.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.example.dto.CsvFileDto;

public class CsvServiceImpl implements CsvService {
  private static final String RESPONSE_VALUE_SEPARATOR = " ";
  private static final String COMMA = ",";

  private final CsvFileDto csvFileDto;

  public CsvServiceImpl(CsvFileDto csvFileDto) {
    this.csvFileDto = csvFileDto;
  }

  @Override
  public List<String> count(String columnName) {
    List<String> content = csvFileDto.getContent();

    String[] headerNames = content.get(0).split(COMMA);
    int columnPosition = findColumnPosition(columnName, headerNames);

    Map<String, Long> map = content.stream()
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
  public List<String> findMax(String columnName) {
    List<String> content = csvFileDto.getContent();
    String[] headerNames = content.get(0).split(COMMA);

    if (Arrays.stream(headerNames).noneMatch(headerName -> headerName.equalsIgnoreCase(columnName))) {
      throw new IllegalArgumentException(String.format("The column \"%s\" was not found", columnName));
    }

    int columnPosition = findColumnPosition(columnName, headerNames);
    int rowPosition = 0;
    int maxValue = Integer.MIN_VALUE;
    for (int i = 1; i < content.size(); i++) {
      int value = Integer.parseInt(content.get(i).split(COMMA)[columnPosition]);
      if (value > maxValue) {
        maxValue = value;
        rowPosition = i;
      }
    }

    return collectResponseForFindMax(content, columnPosition, rowPosition);
  }

  private int findColumnPosition(String columnName, String[] headerNames) {
    int columnPosition = -1;
    for (int i = 0; i < headerNames.length; i++) {
      if (headerNames[i].equalsIgnoreCase(columnName)) {
        columnPosition = i;
        break;
      }
    }
    return columnPosition;
  }

  private List<String> collectResponseForFindMax(List<String> content, int columnPosition, int rowPosition) {
    List<String> result = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    String[] headerNames = content.get(0).split(COMMA);
    sb.append(headerNames[0])
        .append(RESPONSE_VALUE_SEPARATOR)
        .append(headerNames[columnPosition]);
    result.add(sb.toString());

    sb = new StringBuilder();
    String[] rowValues = content.get(rowPosition).split(COMMA);
    sb.append(rowValues[0])
        .append(RESPONSE_VALUE_SEPARATOR)
        .append(rowValues[columnPosition]);
    result.add(sb.toString());
    return result;
  }
}
