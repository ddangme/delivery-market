package com.ddangme.dm.service;

import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Component
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String getReviewFullPath(String filename) {
        return fileDir + "review/" + filename;
    }

    public byte[] getImage(String filename) throws IOException {
        File file = ResourceUtils.getFile(getFullPath(filename));
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encode(imageBytes);
    }

    public List<String> saveReviewPhotos(List<MultipartFile> photos) throws IOException {
        List<String> savePhoto = new ArrayList<>();

        if (photos.isEmpty()) {
            return List.of();
        }

        for (MultipartFile photo : photos) {
            String storeFilename = createStoreFilename(photo.getOriginalFilename());
            savePhoto.add(saveInReviewDirectory(photo, storeFilename));
        }

        return savePhoto;
    }

    public String createStoreFilename(String filename) {
        String ext = extractExt(filename);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");

        return originalFileName.substring(pos + 1);
    }

    private String saveInReviewDirectory(MultipartFile photo, String filename) throws IOException {
        String reviewFullPath = getReviewFullPath(filename);
        photo.transferTo(new File(reviewFullPath));

        return reviewFullPath;
    }
}
