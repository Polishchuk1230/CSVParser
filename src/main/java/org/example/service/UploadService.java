package org.example.service;

import org.example.dto.CsvFileDto;

public interface UploadService {

  CsvFileDto uploadFile(String path);
}
