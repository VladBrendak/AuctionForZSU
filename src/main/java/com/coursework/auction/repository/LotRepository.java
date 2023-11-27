package com.coursework.auction.repository;

import com.coursework.auction.entity.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {

//    @Query("SELECT l FROM Lot l WHERE l.expiration > CURRENT_TIMESTAMP")
//    List<Lot> findAllActiveLots();

    @Query("SELECT l FROM Lot l WHERE l.expiration > CURRENT_TIMESTAMP")
    Page<Lot> findAllActiveLots(Pageable pageable);
    @Query("SELECT l FROM Lot l WHERE l.name_of_lot = :name_of_lot")
    Optional<Lot> findByNameOfLot(@Param("name_of_lot") String nameOfLot);
    @Query("SELECT l.id_lot FROM Lot l WHERE l.name_of_lot = :name_of_lot")
    Long getIdByLotName(@Param("name_of_lot") String nameOfLot);
    @Query("SELECT l FROM Lot l WHERE l.user.id = :userId")
    List<Lot> getLotByUserId(@Param("userId") Long userId);
    @Query("SELECT l.name_of_lot FROM Lot l WHERE l.id_lot = :lotId")
    String findLotNameById(@Param("lotId") Long lotId);
//    List<Lot> findAllByCategory_Id(Long categoryId);
    Page<Lot> findAllByCategory_Id(Long categoryId, Pageable pageable);

//    @Query("SELECT l FROM Lot l WHERE l.name_of_lot LIKE %:partialName%")
//    List<Lot> findAllByName_of_lotContaining(@Param("partialName") String partialName);

    @Query("SELECT l FROM Lot l WHERE l.name_of_lot LIKE %:partialName%")
    Page<Lot> findAllByName_of_lotContaining(@Param("partialName") String partialName, Pageable pageable);
}
