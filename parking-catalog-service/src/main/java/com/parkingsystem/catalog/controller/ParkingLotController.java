package com.parkingsystem.catalog.controller;

import com.parkingsystem.catalog.model.ParkingLot;
import com.parkingsystem.catalog.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {
    
    @Autowired
    private ParkingLotService parkingLotService;
    
    @GetMapping
    public ResponseEntity<List<ParkingLot>> getAllParkingLots(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String facility) {
        
        List<ParkingLot> parkingLots = parkingLotService.getParkingLotsWithFilters(location, type, facility);
        return ResponseEntity.ok(parkingLots);
    }
    
    @GetMapping("/{parkingLotId}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable Long parkingLotId) {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(parkingLotId);
        if (parkingLot == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parkingLot);
    }
    
    @PostMapping
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLot parkingLot) {
        ParkingLot savedParkingLot = parkingLotService.saveParkingLot(parkingLot);
        return ResponseEntity.ok(savedParkingLot);
    }
    
    @DeleteMapping("/{parkingLotId}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long parkingLotId) {
        parkingLotService.deleteParkingLot(parkingLotId);
        return ResponseEntity.ok().build();
    }
}