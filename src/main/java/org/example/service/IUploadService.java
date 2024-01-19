package org.example.service;

import java.io.IOException;
import org.example.dto.FileContentDto;

public interface IUploadService<T> {

  FileContentDto<T> uploadFile(String path) throws IOException;
}
