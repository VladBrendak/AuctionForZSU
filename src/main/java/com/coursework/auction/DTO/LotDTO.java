package com.coursework.auction.DTO;


import com.coursework.auction.entity.Lot;
import com.coursework.auction.entity.LotImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDTO {

    private Long id_lot;
    @NotBlank(message = "Name of lot is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name_of_lot;
    private String titleImage;
    private List<LotImage> lotImages;
    @NotEmpty
    private String category;
    @NotEmpty
    private String description;
    @NotEmpty
    private double startBidAmount;
    @NotEmpty
    private Timestamp expiration;

    public static Lot mapToLot(LotDTO lotdto){
        Lot lot = new Lot();
        lot.setId_lot(lotdto.getId_lot());
        lot.setName_of_lot(lotdto.getName_of_lot());
        lot.setTitleImage(lotdto.getTitleImage());
        lot.setImages(lotdto.getLotImages());
//        lot.setCategory(lotdto.getCategory());
        lot.setDescription(lotdto.getDescription());
        lot.setStartBidAmount(lotdto.getStartBidAmount());
        lot.setExpiration(lotdto.getExpiration());

        return lot;
    }
}
