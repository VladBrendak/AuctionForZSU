package com.coursework.auction.entity;

import com.coursework.auction.DTO.AppUserDTO;
import com.coursework.auction.DTO.AppUserInfoDTO;
import com.coursework.auction.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(name = "login")
    private String username;
    @Column(name = "email", unique=true)
    private String email;
    @Column(name = "user_password")
    private String password;
    @Column(name = "avatarImage")
    private String avatarImage;
    private boolean isTermsOfUseAccepted;
    @OneToMany(mappedBy = "user")
    private List<Lot> lots;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public AppUser(AppUser userInfo) {
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
    }

    public String getThisUsername()
    {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static AppUserDTO mapToDTO(AppUser user)
    {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(user.getId());
        appUserDTO.setUsername(user.getUsername());
        appUserDTO.setEmail(user.getEmail());
        appUserDTO.setPassword("**********");
        return appUserDTO;
    }

    public static AppUserInfoDTO mapToInfoDTO(AppUser user)
    {
        AppUserInfoDTO appUserInfoDTO = new AppUserInfoDTO();
        appUserInfoDTO.setId(user.getId());
        appUserInfoDTO.setUsername(user.getThisUsername());
        appUserInfoDTO.setEmail(user.getEmail());
        appUserInfoDTO.setTermsOfUseAccepted(user.isTermsOfUseAccepted());
        appUserInfoDTO.setAvatarImage(user.getAvatarImage());
        return appUserInfoDTO;
    }
}
