package org.example.service.impl;

import java.util.List;
import org.example.service.OutputService;

public class OutputServiceImpl implements OutputService {

  @Override
  public void sendResponse(List<String> lines) {
    lines.forEach(System.out::println);
  }
}
