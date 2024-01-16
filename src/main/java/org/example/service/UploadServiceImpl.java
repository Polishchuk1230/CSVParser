package org.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.dto.CsvFileDto;

public class UploadServiceImpl implements UploadService {

  @Override
  public CsvFileDto uploadFile(String path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;
      List<String> csvContent = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        csvContent.add(line);
      }
      return new CsvFileDto(csvContent);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
