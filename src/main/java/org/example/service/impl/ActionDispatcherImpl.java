package org.example.service.impl;

import java.util.List;
import org.example.model.Action;
import org.example.service.ActionDispatcher;
import org.example.service.CsvProcessorService;

public class ActionDispatcherImpl implements ActionDispatcher {

  private final CsvProcessorService csvProcessorService;

  public ActionDispatcherImpl(CsvProcessorService csvProcessorService) {
    this.csvProcessorService = csvProcessorService;
  }

  @Override
  public List<String> dispatchAction(String action, String parameter) {
    return switch (Action.valueOf(action.toUpperCase())) {
      case COUNT -> csvProcessorService.count(parameter);
      case FIND_MAX -> csvProcessorService.findMax(parameter);
    };
  }
}
