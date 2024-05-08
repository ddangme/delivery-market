package com.ddangme.dmadmin.service;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadFile getUploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DMAdminException(ErrorCode.FIELD_IS_NULL, "상품 대표 이미지를 선택해주세요.");
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

    public void delete(UploadFile file) {
        File deleteFile = new File(getFullPath(file.getStoreFileName()));

        if (!deleteFile.delete()) {
            throw new DMAdminException(ErrorCode.NOT_EXIST_GOOD_PHOTO);
        }
    }
}
