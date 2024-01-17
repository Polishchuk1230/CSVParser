package org.example.service.impl;

import java.util.List;
import org.example.model.Action;
import org.example.service.CommandService;
import org.example.service.CsvProcessorService;

public class CommandServiceImpl implements CommandService {
  private final CsvProcessorService csvProcessorService;

  public CommandServiceImpl(CsvProcessorService csvProcessorService) {
    this.csvProcessorService = csvProcessorService;
  }

  @Override
  public List<String> processCommand(String action, String columnName) {
    return switch (Action.valueOf(action.toUpperCase())) {
      case COUNT -> csvProcessorService.count(columnName);
      case FIND_MAX -> csvProcessorService.findMax(columnName);
    };
  }
}
