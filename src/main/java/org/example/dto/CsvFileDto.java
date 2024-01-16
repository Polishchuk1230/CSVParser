package org.example.dto;

import java.util.List;

public class CsvFileDto {
  private List<String> content;

  public CsvFileDto() {
  }

  public CsvFileDto(List<String> content) {
    this.content = content;
  }

  public List<String> getContent() {
    return content;
  }

  public void setContent(List<String> content) {
    this.content = content;
  }
}
