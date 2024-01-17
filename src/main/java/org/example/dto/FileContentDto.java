package org.example.dto;

public class FileContentDto<T> {
  private T fileContent;

  public FileContentDto(T fileContent) {
    this.fileContent = fileContent;
  }

  public T getFileContent() {
    return fileContent;
  }

  public void setFileContent(T fileContent) {
    this.fileContent = fileContent;
  }
}
