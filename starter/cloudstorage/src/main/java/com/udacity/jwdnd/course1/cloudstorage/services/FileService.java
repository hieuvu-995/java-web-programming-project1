package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean fileExisted(int userId, String filename) {
        return fileMapper.getByUserIdAndFileName(userId, filename) == null;
    }
    
    public void addFile(File fileUpload){
        fileMapper.insertFile(fileUpload);
    }

    public boolean findByFileName(String filename) {
        File file = fileMapper.getByFileName(filename);

        if(file != null) {
            return false;
        }

        return true;
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }

    public File getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public List<File> getFileByUserId(int userId) {
        return fileMapper.getFileByUser(userId);
    }
    
    public Resource downloadFile(int fileId) {
        try {
            File file = getFileById(fileId);
            Path filePath = Paths.get("uploads");
            var resource = new UrlResource(filePath.resolve(file.getFilename()).toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
