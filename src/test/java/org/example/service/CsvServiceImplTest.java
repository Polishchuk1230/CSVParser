package org.example.service;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.example.dto.CsvFileDto;
import org.junit.Assert;
import org.junit.Test;

public class CsvServiceImplTest extends TestCase {
  private final CsvServiceImpl service;

  {
    CsvFileDto mockCsvFileDto = new CsvFileDto();
    List<String> mockCsvContent = new ArrayList<>();
    mockCsvContent.add("Name,Unit,Prevoius,Title,Description");
    mockCsvContent.add("Dmytro,0,4,QA,Quality Assurance Engineer Level 1");
    mockCsvContent.add("Dmytro,3,2,Junior,Software Engineer Level 1");
    mockCsvContent.add("Olha,7,0,Dev,Software Engineer Level 2");
    mockCsvContent.add("Ihor,11,0,Junior,Software Engineer Level 2");
    mockCsvContent.add("Olha,3,3,Student,Resource Development Lab Student");
    mockCsvContent.add("Dmytro,5,1,QA,Quality Assurance Engineer Level 1");
    mockCsvFileDto.setContent(mockCsvContent);
    service = new CsvServiceImpl(mockCsvFileDto);
  }

  @Test
  public void testCount() {
    List<String> expected = new ArrayList<>();
    expected.add("Name Count");
    expected.add("Dmytro 3");
    expected.add("Olha 2");
    expected.add("Ihor 1");

    List<String> actual = service.count("Name");
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFindMax() {
    List<String> expected = new ArrayList<>();
    expected.add("Name Unit");
    expected.add("Ihor 11");

    List<String> actual = service.findMax("Unit");
    Assert.assertEquals(expected, actual);
  }
}
