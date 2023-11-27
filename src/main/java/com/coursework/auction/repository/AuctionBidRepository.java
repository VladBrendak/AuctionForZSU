package com.coursework.auction.repository;

import com.coursework.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionBidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findTop10ByLotIdOrderByBidDesc(Long lotId);

    @Query("SELECT b FROM Bid b WHERE b.lotId IN (SELECT lotId FROM Bid GROUP BY lotId HAVING MAX(bid) = b.bid) AND b.user.id = :userId")
    List<Bid> findDistinctMaxBidByLotIdForUser(@Param("userId") Long userId);
}
