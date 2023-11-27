package com.coursework.auction.service;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SaveFileService {
    public static String saveFile(MultipartFile file, String storagePath) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String filePath = storagePath + "/" + fileName;

        File destination = new File(filePath);
        try {
            file.transferTo(destination);
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return fileName;
    }

    public static String saveResizedImage(MultipartFile file, String storagePath, int width, int height) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String filePath = storagePath + "/" + fileName;

        File destination = new File(filePath);

        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage resizedImage = resizeImage(originalImage, width, height);

            // Save the resized image
            ImageIO.write(resizedImage, "jpg", destination);
        } catch (IOException ex) {
            System.out.println("Error saving resized image: " + ex.getMessage());
            ex.printStackTrace();
        }

        return fileName;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
