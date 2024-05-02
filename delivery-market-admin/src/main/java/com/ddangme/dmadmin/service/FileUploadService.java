package com.ddangme.dmadmin.service;

import com.ddangme.dmadmin.model.constants.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUploadService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadFile getUploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String originalFileName = file.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);

        return new UploadFile(originalFileName, storeFileName);
    }

    public void transferTo(MultipartFile file, UploadFile uploadFile) throws IOException {
        file.transferTo(new File(getFullPath(uploadFile.getStoreFileName())));
    }


    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");

        return originalFileName.substring(pos + 1);
    }
}
