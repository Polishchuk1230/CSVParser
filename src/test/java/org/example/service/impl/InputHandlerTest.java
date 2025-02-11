package org.example.service.impl;

import static org.example.exception.ExceptionMessage.FILE_NOT_FOUND_EXCEPTION_MESSAGE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.dto.FileContentDto;
import org.example.model.Action;
import org.example.validator.InputValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class InputHandlerTest {
  private static final List<String> TESTED_DATA = List.of(
      "Name,Unit,Prevoius,Title,Description",
      "Dmytro,0,4,QA,Quality Assurance Engineer Level 1",
      "Dmytro,3,2,Junior,Software Engineer Level 1",
      "Olha,7,0,Dev,Software Engineer Level 2",
      "Ihor,11,0,Junior,Software Engineer Level 2",
      "Olha,3,3,Student,Resource Development Lab Student",
      "Dmytro,5,1,QA,Quality Assurance Engineer Level 1");
  private org.example.service.IInputHandler IInputHandler;

  @Before
  public void createInputHandlerImplInstance() throws IOException {
    BufferedReader mockedReader = Mockito.mock(BufferedReader.class);
    Mockito.when(mockedReader.readLine())
        .thenReturn(TESTED_DATA.get(0))
        .thenReturn(null);
    Mockito.when(mockedReader.lines()).thenReturn(TESTED_DATA.stream().skip(1));

    FileContentDto<BufferedReader> fileContentDto = new FileContentDto<>(mockedReader);

    UploadService mockedUploadService = Mockito.mock(UploadService.class);
    Mockito.when(mockedUploadService.uploadFile(ArgumentMatchers.anyString()))
        .thenThrow(FileNotFoundException.class);
    Mockito.when(mockedUploadService.uploadFile(ArgumentMatchers.eq("example_file.csv")))
        .thenReturn(fileContentDto);

    IInputHandler = new InputHandler(
        new InputValidator(),
        mockedUploadService
    );
  }

  @Test
  public void processInputValidCase() {
    List<String> expected = new ArrayList<>();
    expected.add("Name Unit");
    expected.add("Ihor 11");

    String[] inputArgs = new String[] {"example_file.csv", Action.FIND_MAX.toString(), "Unit"};
    List<String> actual = IInputHandler.processInput(inputArgs);

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void processInputFileNotFoundCase() {
    List<String> expected = new ArrayList<>();
    expected.add(FILE_NOT_FOUND_EXCEPTION_MESSAGE);

    String[] inputArgs = new String[] {"not_existed_file.csv", Action.FIND_MAX.toString(), "Unit"};
    List<String> actual = IInputHandler.processInput(inputArgs);

    Assert.assertEquals(expected, actual);
  }
}
