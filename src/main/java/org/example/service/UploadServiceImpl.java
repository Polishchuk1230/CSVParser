package org.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UploadServiceImpl implements UploadService {

  @Override
  public BufferedReader uploadFile(String path) throws IOException {
    return new BufferedReader(new FileReader(path));
  }
}
