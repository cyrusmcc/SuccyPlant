package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.controllers.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Path root = Paths.get("uploads");

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Override
    public void init() {
        try {

            Path blogImgs = Paths.get("uploads/blogs/blogImg");
            Path blogText = Paths.get("uploads/blogs/blogText");
            Path profilePics = Paths.get("uploads/profilePictures");
            Path blog = Paths.get("uploads/blogs");

            if (!Files.exists(root)) Files.createDirectory(root);
            if (!Files.exists(blog)) Files.createDirectory(blog);
            if (!Files.exists(blogImgs)) Files.createDirectory(blogImgs);
            if (!Files.exists(blogText)) Files.createDirectory(blogText);
            if (!Files.exists(profilePics)) Files.createDirectory(profilePics);

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload folder.");
        }
    }

    @Override
    public void save(MultipartFile file, String filePath) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filePath));
            log.info("Saving file: {} to path: {}", file.getName(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    @Override
    public void saveString(String content, Path path, String fileId) {
        try {
            Files.writeString(path.resolve(fileId), content);
            log.info("Saving file: {} to path: {}", fileId, path);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file, Path path, String fileName) {
        try {
            Files.copy(file.getInputStream(), path.resolve(fileName));
            log.info("Saving file: {} to path: {}", file.getName(), path);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }    }

    @Override
    public Resource load(String fileName, Path path) {
        try {
            Path file = path.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("Could not read file {} at path: {}", file, path);
                return null;
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }    }

    public boolean deleteIfExists(Path path, String fileName) {
        try {
            return Files.deleteIfExists(path.resolve(fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load files.");
        }
    }
}
