package com.eleks.auctioneer.entity;

import com.eleks.auctioneer.DTO.LotDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private String image;
    private String author;
    private String category;
    private String description;
    private Timestamp expiration;
    private String asset_file;

//    public Lot(Lot lotInfo){
//        this.name_of_lot=lotInfo.getName_of_lot();
//        this.image=lotInfo.getImage();
//        this.author=lotInfo.getAuthor();
//        this.category=lotInfo.getCategory();
//        this.description=lotInfo.getDescription();
//        this.expiration=lotInfo.getExpiration();
//    }
    public static LotDTO mapToLotDTO(Lot lot){
        LotDTO lotdto = new LotDTO();
        lotdto.setId_lot(lot.getId_lot());
        lotdto.setName_of_lot(lot.getName_of_lot());
        lotdto.setImage(lot.getImage());
        lotdto.setAuthor(lot.getAuthor());
        lotdto.setCategory(lot.getCategory());
        lotdto.setDescription(lot.getDescription());
        lotdto.setExpiration(lot.getExpiration());
        lotdto.setAsset_file(lot.getAsset_file());

        return lotdto;
    }
}
