package com.coursework.auction.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDTOResponse {
    private Long id_lot;
    private String name_of_lot;
    private String authorEmail;
    private String titleImage;
    private List<String> lotImages;
    private String category;
    private String description;
    private double startBidAmount;
    private Timestamp expiration;
}
