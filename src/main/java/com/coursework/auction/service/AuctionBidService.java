package com.coursework.auction.service;

import com.coursework.auction.DTO.BidDTO;
import com.coursework.auction.DTO.BidDTOResponse;
import com.coursework.auction.DTO.UserBidDTO;
import com.coursework.auction.entity.AppUser;
import com.coursework.auction.entity.Bid;
import com.coursework.auction.entity.Lot;
import com.coursework.auction.repository.AuctionBidRepository;
import com.coursework.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionBidService {
    @Autowired
    private AuctionBidRepository auctionBidRepository;
    @Autowired
    private LotRepository lotRepository;

    public ResponseEntity<String> createBid(BidDTO bidDTO, AppUser appUser) {
        if (!isValidBid(bidDTO)) {
            return new ResponseEntity<String>("The betting time has expired!", HttpStatus.BAD_REQUEST);
        }

        Bid bid = BidDTO.mapToBid(bidDTO);
        bid.setUser(appUser);

        auctionBidRepository.save(bid);
        return new ResponseEntity<String>("The bet was placed successfully!", HttpStatus.OK);
    }

    private boolean isValidBid(BidDTO bidDTO) {
        Timestamp expiration = getLotExpiration(bidDTO.getLotId());

        if (expiration != null && expiration.after(new Timestamp(System.currentTimeMillis()))) {
            return true;
        }

        return false;
    }
    private Timestamp getLotExpiration(Long id_lot) {

        Optional<Lot> lotOptional = lotRepository.findById(id_lot);
        if (lotOptional.isPresent()) {
            Lot lot = lotOptional.get();
            return lot.getExpiration();
        }
        return null;
    }

    public List<BidDTOResponse> getBids(String lotName){
        return auctionBidRepository.findTop10ByLotIdOrderByBidDesc(lotRepository.getIdByLotName(lotName))
                .stream()
                .map(Bid::mapToBidDTOResponse)
                .collect(Collectors.toList());
    }

    public List<UserBidDTO> getUserBids(Long userId) {
        List<Bid> bids = auctionBidRepository.findDistinctMaxBidByLotIdForUser(userId);
        List<UserBidDTO> userBids = new ArrayList<>();
        for(Bid b: bids) {
            UserBidDTO userBid = new UserBidDTO();
            userBid.setBid(b.getBid());
            userBid.setDatetime(b.getDatetime());
            userBid.setLotName(lotRepository.findLotNameById(b.getLotId()));
            userBids.add(userBid);
        }
        return userBids;
    }
}
