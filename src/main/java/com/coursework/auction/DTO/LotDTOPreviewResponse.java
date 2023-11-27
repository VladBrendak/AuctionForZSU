package com.coursework.auction.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDTOPreviewResponse {
    private Long id_lot;
    private String name_of_lot;
    private String titleImage;
    private String description;
}