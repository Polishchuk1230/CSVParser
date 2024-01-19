package org.example.service;

import java.util.List;

public interface IOutputHandler {

  void sendResponse(List<String> lines);
}
