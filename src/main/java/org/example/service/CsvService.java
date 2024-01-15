package org.example.service;

import java.util.List;

public interface CsvService {

  List<String> findMax(String columnName);
  List<String> count(String columnName);
}
