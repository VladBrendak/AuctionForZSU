package com.coursework.auction.controller;

import com.coursework.auction.service.UserService;
import com.coursework.auction.DTO.AppUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> addNewUser(@Validated @RequestBody AppUserDTO userInfoDTO) {
        return userService.addUser(userInfoDTO);
    }
}
