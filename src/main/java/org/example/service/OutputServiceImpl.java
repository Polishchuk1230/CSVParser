package org.example.service;

import java.util.List;

public class OutputServiceImpl implements OutputService {

  @Override
  public void sendResponse(List<String> lines) {
    lines.forEach(System.out::println);
  }
}
