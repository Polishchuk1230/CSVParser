package org.example.service;

import java.io.IOException;
import org.example.dto.CsvFileDto;

public interface UploadService {

  CsvFileDto uploadFile(String path) throws IOException;
}
