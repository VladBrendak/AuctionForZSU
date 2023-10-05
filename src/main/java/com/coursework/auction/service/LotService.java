package com.coursework.auction.service;

import com.coursework.auction.DTO.LotDTO;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.entity.Lot;
import com.coursework.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LotService {
    @Value("${auctioneer.app.storage.file-path}")
    private String filePath;

    @Value("${auctioneer.app.storage.image-path}")
    private String imagePath;

    @Autowired
    private LotRepository lotRepository;

    public ResponseEntity<String> uploadLot(LotDTO lotDTO, MultipartFile file, MultipartFile previewImage, AppUser appUser) throws IOException {
        String assetFileName = saveFile(file, filePath);
        String previewImageName = saveFile(previewImage, imagePath);

        Lot lot = LotDTO.mapToLot(lotDTO);
        lot.setAsset_file(assetFileName);
        lot.setImage(previewImageName);
        lot.setUser(appUser);

        lotRepository.save(lot);

        return ResponseEntity.ok("Lot submitted successfully.");
    }

    private String saveFile(MultipartFile file, String storagePath) throws IOException {
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

    public List<LotDTO> getAllActiveLots()
    {
        return lotRepository.findAllActiveLots().stream().map(Lot::mapToLotDTO).toList();
    }
}
