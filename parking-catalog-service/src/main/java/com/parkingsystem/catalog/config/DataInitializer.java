package com.parkingsystem.catalog.config;

import com.parkingsystem.catalog.model.ParkingLot;
import com.parkingsystem.catalog.model.ParkingLotType;
import com.parkingsystem.catalog.model.ParkingSpot;
import com.parkingsystem.catalog.model.ParkingSpotType;
import com.parkingsystem.catalog.repository.ParkingLotRepository;
import com.parkingsystem.catalog.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only create sample data if no parking lots exist
        if (parkingLotRepository.count() == 0) {
            createSampleData();
        }
    }

    private void createSampleData() {
        // Parking Lot 1: Main Campus Surface Lot
        ParkingLot mainCampusLot = new ParkingLot("Main Campus", "P1", ParkingLotType.SURFACE_LOT, 50);
        mainCampusLot.setFacilities(Arrays.asList("Security", "Lighting", "EV Charging", "Covered"));
        parkingLotRepository.save(mainCampusLot);

        // Add parking spots for main campus lot
        for (int i = 1; i <= 50; i++) {
            ParkingSpotType spotType = i <= 5 ? ParkingSpotType.ELECTRIC : 
                                     i <= 10 ? ParkingSpotType.HANDICAP :
                                     i <= 15 ? ParkingSpotType.COMPACT : 
                                     ParkingSpotType.STANDARD;
            
            String position = i <= 10 ? "Near Main Entrance" : 
                            i <= 25 ? "Middle Section" : 
                            "Far Section";
            
            ParkingSpot spot = new ParkingSpot("A" + i, mainCampusLot, spotType, position);
            if (i > 45) {
                spot.setAvailable(false); // Mark some spots as unavailable
            }
            parkingSpotRepository.save(spot);
        }

        // Parking Lot 2: North Garage
        ParkingLot northGarage = new ParkingLot("North Campus", "P2", ParkingLotType.PARKING_GARAGE, 100);
        northGarage.setFacilities(Arrays.asList("Security", "Lighting", "Elevator", "Covered", "24/7 Access"));
        parkingLotRepository.save(northGarage);

        // Add parking spots for north garage
        String[] garageLevels = {"Level 1", "Level 2", "Level 3", "Level 4"};
        for (int i = 1; i <= 100; i++) {
            int levelIndex = (i - 1) / 25;
            ParkingSpotType spotType = i <= 10 ? ParkingSpotType.RESERVED :
                                     i <= 20 ? ParkingSpotType.ELECTRIC :
                                     i <= 30 ? ParkingSpotType.HANDICAP :
                                     ParkingSpotType.STANDARD;
            
            ParkingSpot spot = new ParkingSpot("B" + i, northGarage, spotType, garageLevels[levelIndex]);
            parkingSpotRepository.save(spot);
        }

        // Parking Lot 3: Visitor Parking
        ParkingLot visitorLot = new ParkingLot("Visitor Center", "P3", ParkingLotType.VISITOR, 20);
        visitorLot.setFacilities(Arrays.asList("Security", "Lighting", "Pay Station", "Short-term"));
        parkingLotRepository.save(visitorLot);

        // Add parking spots for visitor lot
        for (int i = 1; i <= 20; i++) {
            ParkingSpotType spotType = i <= 2 ? ParkingSpotType.HANDICAP : ParkingSpotType.STANDARD;
            ParkingSpot spot = new ParkingSpot("V" + i, visitorLot, spotType, "Visitor Area");
            parkingSpotRepository.save(spot);
        }

        System.out.println("Sample parking data created successfully!");
    }
}