package org.example.service.impl;

import java.util.List;
import org.example.service.IOutputHandler;

public class OutputHandler implements IOutputHandler {

  @Override
  public void sendResponse(List<String> lines) {
    lines.forEach(System.out::println);
  }
}
