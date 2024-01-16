package org.example.service;

import java.util.List;
import org.example.model.Action;

public class CommandServiceImpl implements CommandService {
  private final CsvService csvService;

  public CommandServiceImpl(CsvService csvService) {
    this.csvService = csvService;
  }

  @Override
  public List<String> processCommand(String action, String columnName) {
    return switch (Action.valueOf(action.toUpperCase())) {
      case COUNT -> csvService.count(columnName);
      case FIND_MAX -> csvService.findMax(columnName);
    };
  }
}
