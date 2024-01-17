package org.example.service;

import java.util.List;

public interface CsvProcessorService {

  List<String> findMax(String columnName);
  List<String> count(String columnName);
}
