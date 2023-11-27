package com.coursework.auction.entity;

import com.coursework.auction.DTO.LotDTO;
import com.coursework.auction.DTO.LotDTOPreviewResponse;
import com.coursework.auction.DTO.LotDTOResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lot")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lot;
    @Column(unique = true)
    private String name_of_lot;
    private String titleImage;
    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotImage> images;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double startBidAmount;
    private Timestamp expiration;

    public static LotDTO mapToLotDTO(Lot lot){
        LotDTO lotdto = new LotDTO();
        lotdto.setId_lot(lot.getId_lot());
        lotdto.setName_of_lot(lot.getName_of_lot());
        lotdto.setTitleImage(lot.getTitleImage());
        lotdto.setLotImages(lot.getImages());
//        lotdto.setAuthorEmail(lot.getUser().getEmail());
        lotdto.setCategory(lot.getCategory().getCategory());
        lotdto.setDescription(lot.getDescription());
        lotdto.setStartBidAmount(lotdto.getStartBidAmount());
        lotdto.setExpiration(lot.getExpiration());

        return lotdto;
    }

    public static LotDTOResponse mapToLotDTOResponse(Lot lot){
        LotDTOResponse lotDTOResponse = new LotDTOResponse();
        lotDTOResponse.setId_lot(lot.getId_lot());
        lotDTOResponse.setName_of_lot(lot.getName_of_lot());
        lotDTOResponse.setTitleImage(lot.getTitleImage());
        lotDTOResponse.setLotImages(lot.getImages().stream().map(LotImage::getImageUrl).collect(Collectors.toList()));
        lotDTOResponse.setAuthorEmail(lot.getUser().getEmail());
        lotDTOResponse.setCategory(lot.getCategory().getCategory());
        lotDTOResponse.setDescription(lot.getDescription());
        lotDTOResponse.setStartBidAmount(lot.getStartBidAmount());
        lotDTOResponse.setExpiration(lot.getExpiration());

        return lotDTOResponse;
    }

    public static LotDTOPreviewResponse mapToLotDTOPreviewResponse(Lot lot){
        LotDTOPreviewResponse lotDTOPreviewResponse = new LotDTOPreviewResponse();
        lotDTOPreviewResponse.setId_lot(lot.getId_lot());
        lotDTOPreviewResponse.setName_of_lot(lot.getName_of_lot());
        lotDTOPreviewResponse.setTitleImage(lot.getTitleImage());
        lotDTOPreviewResponse.setDescription(lot.getDescription());

        return lotDTOPreviewResponse;
    }
}
