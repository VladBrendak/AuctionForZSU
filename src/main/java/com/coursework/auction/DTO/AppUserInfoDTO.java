package com.coursework.auction.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserInfoDTO {
    private Long id;
    private String username;
    private String email;
    private String avatarImage;
    private boolean isTermsOfUseAccepted;
}
