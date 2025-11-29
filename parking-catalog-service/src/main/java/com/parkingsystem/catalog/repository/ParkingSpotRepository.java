package com.parkingsystem.catalog.repository;

import com.parkingsystem.catalog.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    
    List<ParkingSpot> findByParkingLotId(Long parkingLotId);
    
    @Query("SELECT ps FROM ParkingSpot ps WHERE ps.parkingLot.id = :parkingLotId AND ps.available = true")
    List<ParkingSpot> findAvailableSpotsByParkingLotId(@Param("parkingLotId") Long parkingLotId);
    
    @Query("SELECT ps FROM ParkingSpot ps WHERE ps.type = :spotType AND ps.available = true")
    List<ParkingSpot> findAvailableSpotsByType(@Param("spotType") String spotType);
    
    ParkingSpot findByParkingLotIdAndSpotNumber(Long parkingLotId, String spotNumber);
}