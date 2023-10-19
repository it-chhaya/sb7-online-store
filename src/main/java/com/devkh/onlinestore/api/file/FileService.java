package com.devkh.onlinestore.api.file;

import com.devkh.onlinestore.api.file.web.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void deleteAll();

    void deleteByName(String name);

    List<FileDto> findAll();

    FileDto findByName(String name) throws IOException;

    FileDto uploadSingle(MultipartFile file);

    List<FileDto> uploadMultiple(List<MultipartFile> files);

}
