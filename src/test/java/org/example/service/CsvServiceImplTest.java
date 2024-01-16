package org.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.example.exception.ColumnNotFountException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CsvServiceImplTest {
  private CsvService csvService;

  @Before
  public void createCsvService() throws IOException {
    BufferedReader mockedReader = Mockito.mock(BufferedReader.class);
    Mockito.when(mockedReader.readLine())
        .thenReturn("Name,Unit,Prevoius,Title,Description")
        .thenReturn(null);
    Mockito.when(mockedReader.lines()).thenReturn(
        Stream.of(
            "Dmytro,0,4,QA,Quality Assurance Engineer Level 1",
            "Dmytro,3,2,Junior,Software Engineer Level 1",
            "Olha,7,0,Dev,Software Engineer Level 2",
            "Ihor,11,0,Junior,Software Engineer Level 2",
            "Olha,3,3,Student,Resource Development Lab Student",
            "Dmytro,5,1,QA,Quality Assurance Engineer Level 1"));
    csvService = new CsvServiceImpl(mockedReader);
  }

  @Test
  public void testCountValidCase() throws IOException {
    List<String> expected = new ArrayList<>();
    expected.add("Name Count");
    expected.add("Dmytro 3");
    expected.add("Olha 2");
    expected.add("Ihor 1");

    List<String> actual = csvService.count("Name");
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = ColumnNotFountException.class)
  public void testCountColumnNotExists() throws IOException {
    csvService.count("Salary");
  }

  @Test
  public void testFindMaxValidCase() throws IOException {
    List<String> expected = new ArrayList<>();
    expected.add("Name Unit");
    expected.add("Ihor 11");

    List<String> actual = csvService.findMax("Unit");
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = ColumnNotFountException.class)
  public void testFindMaxColumnNotExists() throws IOException {
    csvService.findMax("Salary");
  }

  @Test(expected = NumberFormatException.class)
  public void testFindMaxColumnNotNumeric() throws IOException {
    List<String> expected = new ArrayList<>();

    List<String> actual = csvService.findMax("Title");
    Assert.assertEquals(expected, actual);
  }
}
