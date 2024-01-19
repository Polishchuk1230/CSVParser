package org.example.service.action.commant;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.example.dto.FileContentDto;

public class ActionCommandCount extends AActionCommand {

  public ActionCommandCount(FileContentDto<BufferedReader> fileContentDto) {
    super(fileContentDto);
  }

  @Override
  public List<String> execute(String... parameters) {
    String columnName = parameters[0];
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
    Comparator<Entry<String, Long>> compByValue = Comparator.comparingLong(Entry::getValue);
    List<String> result = contentMap.entrySet().stream()
        .sorted(compByValue.reversed())
        .map(entry -> entry.getKey() + RESPONSE_VALUE_SEPARATOR + entry.getValue())
        .collect(Collectors.toList());
    result.add(0, columnName + RESPONSE_VALUE_SEPARATOR + "Count");
    return result;
  }
}
