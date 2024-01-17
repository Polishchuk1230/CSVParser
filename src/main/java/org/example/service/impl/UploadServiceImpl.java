package org.example.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.example.dto.FileContentDto;
import org.example.service.UploadService;

public class UploadServiceImpl implements UploadService {

  @Override
  public FileContentDto<BufferedReader> uploadFile(String path) throws IOException {
    return new FileContentDto<>(
        new BufferedReader(new FileReader(path)));
  }
}
