package com.devkh.onlinestore.api.file;

import com.devkh.onlinestore.api.file.web.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.server-path}")
    private String serverPath;

    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Value("${file.download-uri}")
    private String fileDownloadUri;

    @Override
    public Resource downloadByName(String name) {

        Path path = Paths.get(serverPath + name);

        if (Files.exists(path)) {
            // Start loading the file by name
            return UrlResource.from(path.toUri());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Resource is not found");
    }

    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(serverPath + name);
        try {
            boolean isDeleted = Files.deleteIfExists(path);
            if (!isDeleted)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Resource is not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        Path path = Paths.get(serverPath);
        try {
            Stream<Path> paths = Files.list(path);
            List<Path> pathList = paths.toList();
            for (Path p : pathList) {
                Files.delete(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FileDto> findAll() {

        List<FileDto> fileDtoList = new ArrayList<>();

        Path path = Paths.get(serverPath);

        try {

            Stream<Path> paths = Files.list(path);
            List<Path> pathList = paths.toList();

            for (Path p : pathList) {
                Resource resource = UrlResource.from(p.toUri());
                fileDtoList.add(FileDto.builder()
                        .name(resource.getFilename())
                        .uri(fileBaseUri + resource.getFilename())
                        .downloadUri(fileDownloadUri + resource.getFilename())
                        .extension(this.getExtension(resource.getFilename()))
                        .size(resource.contentLength())
                        .build());
            }

            return fileDtoList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileDto findByName(String name) throws IOException {
        Path path = Paths.get(serverPath + name);
        if (Files.exists(path)) {
            // Start loading the file by name
            Resource resource = UrlResource.from(path.toUri());
            return FileDto.builder()
                    .name(name)
                    .uri(fileBaseUri + name)
                    .downloadUri(fileDownloadUri + resource.getFilename())
                    .extension(this.getExtension(name))
                    .size(resource.contentLength())
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Resource is not found");
    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return this.save(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        return files.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    private String getExtension(String fileName) {
        // Get file extension
        int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex + 1);
    }

    private FileDto save(MultipartFile file) {

        String extension = this.getExtension(file.getOriginalFilename());
        String name = UUID.randomUUID() + "." + extension;
        String uri = fileBaseUri + name;
        Long size = file.getSize();

        // Create file path
        Path path = Paths.get(serverPath + name);

        // Copy file
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileDto.builder()
                .name(name)
                .uri(uri)
                .downloadUri(fileDownloadUri + name)
                .extension(extension)
                .size(size)
                .build();
    }

}
