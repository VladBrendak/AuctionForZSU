package com.coursework.auction.DTO;

import com.coursework.auction.entity.Bid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private Long bidId;
    private Long id_user;
    private Long lotId;
    private Long bid;
    private Timestamp datetime;
    public static Bid mapToBid(BidDTO bidDTO){
        Bid bid = new Bid();
        bid.setBidId(bidDTO.getBidId());
        bid.setLotId(bidDTO.getLotId());
        bid.setBid(bidDTO.getBid());
        bid.setDatetime(bidDTO.getDatetime());

        return bid;
    }

}
