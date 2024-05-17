package com.ddangme.dm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Component
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public byte[] getImage(String filename) throws IOException {
        File file = ResourceUtils.getFile(getFullPath(filename));
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encode(imageBytes);
    }

}
