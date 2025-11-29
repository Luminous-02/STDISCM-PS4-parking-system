package com.parkingsystem.catalog.service;

import com.parkingsystem.catalog.model.ParkingLot;
import com.parkingsystem.catalog.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {
    
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }
    
    public List<ParkingLot> getParkingLotsWithFilters(String location, String type, String facility) {
        if (location != null) {
            return parkingLotRepository.findByLocation(location);
        } else if (type != null) {
            return parkingLotRepository.findByType(type);
        } else if (facility != null) {
            return parkingLotRepository.findByFacility(facility);
        } else {
            return parkingLotRepository.findAll();
        }
    }
    
    public ParkingLot getParkingLotById(Long id) {
        return parkingLotRepository.findById(id).orElse(null);
    }
    
    public ParkingLot saveParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }
    
    public void deleteParkingLot(Long id) {
        parkingLotRepository.deleteById(id);
    }
}