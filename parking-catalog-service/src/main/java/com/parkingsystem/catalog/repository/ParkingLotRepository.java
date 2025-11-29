package com.parkingsystem.catalog.repository;

import com.parkingsystem.catalog.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    
    List<ParkingLot> findByLocation(String location);
    
    List<ParkingLot> findByType(String type);
    
    @Query("SELECT p FROM ParkingLot p WHERE :facility MEMBER OF p.facilities")
    List<ParkingLot> findByFacility(@Param("facility") String facility);
    
    ParkingLot findByLotNumber(String lotNumber);
}