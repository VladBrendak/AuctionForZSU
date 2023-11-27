package com.coursework.auction.controller;

import com.coursework.auction.DTO.LotDTO;
import com.coursework.auction.DTO.LotDTOPreviewResponse;
import com.coursework.auction.DTO.LotDTOResponse;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.entity.Lot;
import com.coursework.auction.service.LotService;
import com.coursework.auction.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;



@RestController
@RequestMapping("/lots")
public class LotController {
    @Value("${auctioneer.app.storage.file-path}")
    private String filePath;
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<String> uploadLot(@RequestParam ("model") String model,
                                            @RequestParam("previewImage") MultipartFile previewImage,
                                            @RequestParam("images") List<MultipartFile> images,
                                            Principal principal) throws IOException {

        String currentUserEmail = principal.getName();
        AppUser appUser = userService.getUserByEmail(currentUserEmail);
        var lotDTO = objectMapper.readValue(model, LotDTO.class);
        return lotService.uploadLot(lotDTO, previewImage, images, appUser);
    }

//    @GetMapping("/active")
//    public List<LotDTOResponse> getActiveLots()
//    {
//        return lotService.getAllActiveLots();
//    }

//    @GetMapping("/activePreview")
//    public List<LotDTOPreviewResponse> getActiveLotsPreview()
//    {
//        return lotService.getAllActiveLotsPreview();
//    }

//    @GetMapping("/category/{categoryId}")
//    public List<LotDTOPreviewResponse> getLotsByCategory(@PathVariable("categoryId") Long categoryId)
//    {
//        return lotService.getLotsByCategoryName(categoryId);
//    }

//    @GetMapping("/search/{lotName}")
//    public List<LotDTOPreviewResponse> searchLotsByName(@PathVariable("lotName") String lotName)
//    {
//        return lotService.searchLotsByName(lotName);
//    }

    @GetMapping("/active")
    public ResponseEntity<Page<LotDTOResponse>> getActiveLots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LotDTOResponse> lotsPreview = lotService.getAllActiveLots(page, size);
        return ResponseEntity.ok(lotsPreview);
    }

    @GetMapping("/activePreview")
    public ResponseEntity<Page<LotDTOPreviewResponse>> getActiveLotsPreview(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LotDTOPreviewResponse> lotsPreview = lotService.getAllActiveLotsPreview(page, size);
        return ResponseEntity.ok(lotsPreview);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<LotDTOPreviewResponse>> getLotsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LotDTOPreviewResponse> lotsPreview = lotService.getLotsByCategoryName(categoryId, page, size);
        return ResponseEntity.ok(lotsPreview);
    }

    @GetMapping("/search/{lotName}")
    public ResponseEntity<Page<LotDTOPreviewResponse>> searchLotsByName(
            @PathVariable("lotName") String lotName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<LotDTOPreviewResponse> lotsPreview =lotService.searchLotsByName(lotName, page, size);
        return ResponseEntity.ok(lotsPreview);
    }

    @GetMapping("lot/{lotName}")
    public LotDTOResponse getLot(@PathVariable("lotName") String lotName)
    {
        return lotService.getLot(lotName);
    }

    @GetMapping("/user/{userId}")
    public List<LotDTOPreviewResponse> getUserLots(@PathVariable("userId") Long userId)
    {
        return lotService.getAllUserLots(userId);
    }

    @RequestMapping(value = "/files/{fileReference}", method = RequestMethod.GET)
    public ResponseEntity<StreamingResponseBody> getStreamingFile(@PathVariable("fileReference") String fileReference, HttpServletResponse response) {
        try {
            Path folderPath = Paths.get(filePath);
            if (!Files.exists(folderPath)) {
                return ResponseEntity.notFound().build();
            }

            String fileExtension = "";
            try (Stream<Path> paths = Files.walk(folderPath)) {
                Optional<Path> optionalFilePath = paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().startsWith(fileReference))
                        .findFirst();
                if (optionalFilePath.isPresent()) {
                    Path filePath = optionalFilePath.get();
                    fileExtension = getFileExtension(filePath);

                    String mimeType = Files.probeContentType(filePath);
                    response.setContentType(mimeType);
                    response.setHeader("Content-Disposition", "attachment; filename=\"demo" + fileExtension + "\"");

                    InputStream inputStream = Files.newInputStream(filePath);

                    StreamingResponseBody responseBody = outputStream -> {
                        int nRead;
                        byte[] data = new byte[1024];
                        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                            System.out.println("Writing some bytes..");
                            outputStream.write(data, 0, nRead);
                        }
                    };

                    return ResponseEntity.ok(responseBody);
                }
            }

            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            System.out.println(ade.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String getFileExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex);
    }
}
