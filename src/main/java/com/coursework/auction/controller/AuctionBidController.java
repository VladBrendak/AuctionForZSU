package com.coursework.auction.controller;

import com.coursework.auction.DTO.BidDTO;
import com.coursework.auction.service.UserService;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.service.AuctionBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/bids")
public class AuctionBidController {
    @Autowired
    private AuctionBidService auctionBidService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> makeBid(@Validated @RequestBody BidDTO bidDTO, Principal principal) {
        String currentUserEmail = principal.getName();
        AppUser appUser = userService.getUserByEmail(currentUserEmail);
        return auctionBidService.createBid(bidDTO, appUser);
    }
}
