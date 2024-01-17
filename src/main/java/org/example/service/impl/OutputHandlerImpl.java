package org.example.service.impl;

import java.util.List;
import org.example.service.OutputHandler;

public class OutputHandlerImpl implements OutputHandler {

  @Override
  public void sendResponse(List<String> lines) {
    lines.forEach(System.out::println);
  }
}
