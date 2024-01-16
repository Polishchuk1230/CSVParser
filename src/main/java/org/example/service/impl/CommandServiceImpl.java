package org.example.service.impl;

import java.io.IOException;
import java.util.List;
import org.example.model.Action;
import org.example.service.CommandService;
import org.example.service.CsvService;

public class CommandServiceImpl implements CommandService, AutoCloseable {
  private final CsvService csvService;

  public CommandServiceImpl(CsvService csvService) {
    this.csvService = csvService;
  }

  @Override
  public List<String> processCommand(String action, String columnName) throws IOException {
    return switch (Action.valueOf(action.toUpperCase())) {
      case COUNT -> csvService.count(columnName);
      case FIND_MAX -> csvService.findMax(columnName);
    };
  }

  @Override
  public void close() throws Exception {
    if (csvService instanceof AutoCloseable) {
      ((AutoCloseable) csvService).close();
    }
  }
}
