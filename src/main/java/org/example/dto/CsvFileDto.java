package org.example.dto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileDto {
  private List<String> content;

  public CsvFileDto() {
  }

  public CsvFileDto(String filePath) {
    fetchCsv(filePath);
  }

  public List<String> getContent() {
    return content;
  }

  public void setContent(List<String> content) {
    this.content = content;
  }

  private void fetchCsv(String path) {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;
      List<String> csvContent = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        csvContent.add(line);
      }
      content = csvContent;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
