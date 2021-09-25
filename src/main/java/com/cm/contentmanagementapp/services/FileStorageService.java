package com.cm.contentmanagementapp.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    public void init();

    public void save(MultipartFile file, String filePath);

    public void save(MultipartFile file, Path path, String fileName);

    public Resource load(String fileName);

    public Resource load(String fileName, Path path);

    public boolean deleteIfExists(Path path, String fileName);

    public void deleteAll();

    public Stream<Path> loadAll();

}
