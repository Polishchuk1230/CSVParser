package org.example.service;

import java.util.List;

public interface CommandService {

  List<String> processCommand(String action, String columnName);
}
