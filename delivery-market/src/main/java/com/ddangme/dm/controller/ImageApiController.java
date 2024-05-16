package com.ddangme.dm.controller;

import com.ddangme.dm.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final FileService fileService;

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        File file = ResourceUtils.getFile(fileService.getFullPath(filename));
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        byte[] base64ImageBytes = Base64.getEncoder().encode(imageBytes);
        return ResponseEntity.ok().body(base64ImageBytes);
    }
}
