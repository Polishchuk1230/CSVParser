package org.example.service.action.commant;

import static org.example.exception.ExceptionMessage.TABLE_IS_EMPTY_EXCEPTION_MESSAGE;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.example.dto.FileContentDto;
import org.example.exception.TableIsEmptyException;

public class ActionCommandFindMax extends AActionCommand {

  public ActionCommandFindMax(FileContentDto<BufferedReader> fileContentDto) {
    super(fileContentDto);
  }

  @Override
  public List<String> execute(String... parameters) {
    String columnName = parameters[0];
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
}
