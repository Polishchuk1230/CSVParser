package org.example.service;

import java.util.List;
import org.example.dto.CsvFileDto;

public class CommandService {
  private final CsvService csvService;

  public CommandService(String fileName) {
    this.csvService = new CsvServiceImpl(new CsvFileDto(fileName));
  }

  public List<String> processCommand(String action, String columnName) {
    return switch (Actions.valueOf(action.toUpperCase())) {
      case COUNT -> csvService.count(columnName);
      case FIND_MAX -> csvService.findMax(columnName);
    };
  }

  private enum Actions {
    COUNT, FIND_MAX
  }
}
