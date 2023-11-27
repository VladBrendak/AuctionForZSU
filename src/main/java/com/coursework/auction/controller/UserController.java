package com.coursework.auction.controller;

import com.coursework.auction.DTO.AppUserDTO;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<String> addNewUser(@Validated @RequestBody AppUserDTO userInfoDTO) {
        return userService.addUser(userInfoDTO);
    }

    @PutMapping("/acceptTermsOfUse")
    public ResponseEntity<Void> acceptTermsOfUse(Principal principal) {
        String currentUserEmail = principal.getName();
        AppUser appUser = userService.getUserByEmail(currentUserEmail);
        userService.acceptTermsOfUse(appUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getTermsOfUseState")
    public ResponseEntity<Boolean> getTermsOfUseState(Principal principal) {
        if(principal != null)
        {
            String currentUserEmail = principal.getName();
            AppUser appUser = userService.getUserByEmail(currentUserEmail);
            return ResponseEntity.ok(appUser.isTermsOfUseAccepted());
        }
        return ResponseEntity.ok(false);
    }
}
