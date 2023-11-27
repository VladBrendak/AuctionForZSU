package com.coursework.auction.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBidDTO {
    private String lotName;
    private Long bid;
    private Timestamp datetime;
}