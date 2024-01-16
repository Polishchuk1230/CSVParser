package org.example.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface UploadService {

  BufferedReader uploadFile(String path) throws IOException;
}
