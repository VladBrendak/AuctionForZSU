package com.coursework.auction.repository;

import com.coursework.auction.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    @Query("SELECT u.username FROM AppUser u WHERE u.id = :id_user")
    String getNameById(@Param("id_user") Long id_user);
}
