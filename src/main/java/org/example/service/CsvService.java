package org.example.service;

import java.io.IOException;
import java.util.List;

public interface CsvService {

  List<String> findMax(String columnName) throws IOException;
  List<String> count(String columnName) throws IOException;
}
