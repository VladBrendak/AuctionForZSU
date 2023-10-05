package com.coursework.auction.entity;

import com.coursework.auction.DTO.LotDTO;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    private String category;
    private String description;
    private Timestamp expiration;
    private String asset_file;

    public static LotDTO mapToLotDTO(Lot lot){
        LotDTO lotdto = new LotDTO();
        lotdto.setId_lot(lot.getId_lot());
        lotdto.setName_of_lot(lot.getName_of_lot());
        lotdto.setAuthorEmail(lot.getUser().getEmail());
        lotdto.setCategory(lot.getCategory());
        lotdto.setDescription(lot.getDescription());
        lotdto.setExpiration(lot.getExpiration());

        return lotdto;
    }
}
