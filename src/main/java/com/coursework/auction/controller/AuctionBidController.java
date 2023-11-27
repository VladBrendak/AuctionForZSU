package com.coursework.auction.controller;

import com.coursework.auction.DTO.BidDTO;
import com.coursework.auction.DTO.BidDTOResponse;
import com.coursework.auction.DTO.UserBidDTO;
import com.coursework.auction.service.UserService;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.service.AuctionBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/auction/{lotName}")
    public List<BidDTOResponse> getBids(@PathVariable("lotName") String lotName) {
        return auctionBidService.getBids(lotName);
    }

//    @GetMapping("/user/{userId}")
//    public List<UserBidDTO> getUserBids(@PathVariable("userId") Long userId) {
//        return auctionBidService.getUserBids(userId);
//    }

    @GetMapping("/user")
    public List<UserBidDTO> getUserBids(Principal principal) {
        String currentUserEmail = principal.getName();
        AppUser appUser = userService.getUserByEmail(currentUserEmail);
        return auctionBidService.getUserBids(appUser.getId());
    }
}
