package com.coursework.auction.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    @Value("${auctioneer.app.storage.image-path}")
    private String imagesPath;

    @GetMapping(value = "/getImage/{imageName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(imagesPath, imageName);

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            MediaType contentType = MediaType.parseMediaType(Files.probeContentType(imagePath).toString());
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(imageName)
                    .build();

            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header("Content-Disposition", "attachment; filename=\"" + imageName + "\"")
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

