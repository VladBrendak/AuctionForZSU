package com.coursework.auction.auth;

import com.coursework.auction.DTO.AppUserInfoDTO;
import com.coursework.auction.config.JwtService;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.service.LotService;
import com.coursework.auction.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Value("${auctioneer.app.storage.image-path}")
    private String imagesPath;
    private final AuthenticationService service;
    private final JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private LotService lotService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestParam ("userModel") String model,
            @RequestParam("userAvatarImg") MultipartFile userAvatarImg) throws IOException {

        var registerRequest = objectMapper.readValue(model, RegisterRequest.class);
        return ResponseEntity.ok(service.register(registerRequest, userAvatarImg));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/isTokenValid")
    public ResponseEntity<Boolean> checkTokenValidity(HttpServletRequest request, Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = extractTokenFromHeader(request); // Extract the token from the request header
            boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
            return ResponseEntity.ok(isTokenValid);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/getUserInfo")
    public AppUserInfoDTO getUserInfo(HttpServletRequest request) throws IOException {
            String token = extractTokenFromHeader(request);
            String email = jwtService.extractUsername(token);
            AppUserInfoDTO userInfoDTO = AppUser.mapToInfoDTO(userService.getUserByEmail(email));
        return userInfoDTO;
    }

    private byte[] loadAvatarImageBytes(String imageName) throws IOException {
        Path imagePath = Paths.get(imagesPath, imageName);

        if (Files.exists(imagePath)) {
            return Files.readAllBytes(imagePath);
        } else {
            return new byte[0];
        }
    }

    // Method to extract the token from the request header
    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null; // Return null if the token is not found
    }
}
