package com.coursework.auction.repository;

import com.coursework.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionBidRepository extends JpaRepository<Bid, Long> {

}
