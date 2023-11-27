package com.coursework.auction.service;

import com.coursework.auction.DTO.LotDTO;
import com.coursework.auction.DTO.LotDTOPreviewResponse;
import com.coursework.auction.DTO.LotDTOResponse;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.entity.Lot;
import com.coursework.auction.entity.LotImage;
import com.coursework.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LotService {
    @Value("${auctioneer.app.storage.file-path}")
    private String filePath;

    @Value("${auctioneer.app.storage.image-path}")
    private String imagePath;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private CategoryServise categoryServise;

//    public ResponseEntity<String> uploadLot(LotDTO lotDTO, MultipartFile file, MultipartFile previewImage, AppUser appUser) throws IOException {
//        String assetFileName = saveFile(file, filePath);
//        String previewImageName = saveFile(previewImage, imagePath);
//
//        Lot lot = LotDTO.mapToLot(lotDTO);
//        lot.setAsset_file(assetFileName);
//        lot.setTitleImage(previewImageName);
//        lot.setUser(appUser);
//
//        lotRepository.save(lot);
//
//        return ResponseEntity.ok("Lot submitted successfully.");
//    }

    public ResponseEntity<String> uploadLot(LotDTO lotDTO, MultipartFile previewImage, List<MultipartFile> images, AppUser appUser) throws IOException {
        String previewImageName = SaveFileService.saveFile(previewImage, imagePath);
        List<LotImage> lotImageList = new ArrayList<>();

        Lot lot = LotDTO.mapToLot(lotDTO);
        lot.setTitleImage(previewImageName);
        lot.setUser(appUser);
        lot.setCategory(categoryServise.getCategory(lotDTO.getCategory()));

        lotRepository.save(lot);

        Optional<Lot> l = lotRepository.findByNameOfLot(lot.getName_of_lot());
        if(l.isPresent())
        {
            lot = l.get();
        } else {
            return ResponseEntity.ok("Optional<Lot> is NOT present");
        }

        for(MultipartFile mf: images)
        {
            LotImage li = new LotImage();
            li.setLot(lot);
            li.setImageUrl(SaveFileService.saveFile(mf, imagePath));
            lotImageList.add(li);
        }

        lot.setImages(lotImageList);

        lotRepository.save(lot);

        return ResponseEntity.ok("Lot submitted successfully.");
    }

    public LotDTOResponse getLot(String lotName)
    {
        return Lot.mapToLotDTOResponse(lotRepository.findByNameOfLot(lotName).get());
    }

//    public List<LotDTOResponse> getAllActiveLots()
//    {
//        return lotRepository.findAllActiveLots().stream().map(Lot::mapToLotDTOResponse).toList();
//    }

//    public List<LotDTOPreviewResponse> getAllActiveLotsPreview()
//    {
//        return lotRepository.findAllActiveLots().stream().map(Lot::mapToLotDTOPreviewResponse).toList();
//    }

//    public List<LotDTOPreviewResponse> getLotsByCategoryName(Long categoryId) {
//        return lotRepository.findAllByCategory_Id(categoryId).stream().map(Lot::mapToLotDTOPreviewResponse).toList();
//    }

//    public List<LotDTOPreviewResponse> searchLotsByName(String lotName) {
//        return lotRepository.findAllByName_of_lotContaining(lotName).stream().map(Lot::mapToLotDTOPreviewResponse).toList();
//    }

    public Page<LotDTOResponse> getAllActiveLots(int page, int size)
    {
        PageRequest pageable = PageRequest.of(page, size);
        return lotRepository.findAllActiveLots(pageable)
                .map(Lot::mapToLotDTOResponse);
    }

    public Page<LotDTOPreviewResponse> getAllActiveLotsPreview(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return lotRepository.findAllActiveLots(pageable)
                .map(Lot::mapToLotDTOPreviewResponse);
    }

    public Page<LotDTOPreviewResponse> getLotsByCategoryName(Long categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return lotRepository.findAllByCategory_Id(categoryId, pageable)
                .map(Lot::mapToLotDTOPreviewResponse);
    }

    public Page<LotDTOPreviewResponse> searchLotsByName(String lotName, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return lotRepository.findAllByName_of_lotContaining(lotName, pageable)
                .map(Lot::mapToLotDTOPreviewResponse);
    }

    public List<LotDTOPreviewResponse> getAllUserLots(Long userId)
    {
        return lotRepository.getLotByUserId(userId).stream().map(Lot::mapToLotDTOPreviewResponse).collect(Collectors.toList());
    }
}
