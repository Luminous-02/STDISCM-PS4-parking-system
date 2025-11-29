package com.parkingsystem.catalog.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lots", schema = "catalog")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String location;  // "Main Campus", "North Garage", etc.
    
    @Column(name = "lot_number", nullable = false, unique = true)
    private String lotNumber; // "P1", "P2", "P3"
    
    @Column(name = "total_spots")
    private Integer totalSpots;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingLotType type;
    
    @ElementCollection
    @CollectionTable(name = "parking_lot_facilities", schema = "catalog", 
                     joinColumns = @JoinColumn(name = "parking_lot_id"))
    @Column(name = "facility")
    private List<String> facilities = new ArrayList<>();
    
    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingSpot> parkingSpots = new ArrayList<>();
    
    // Constructors
    public ParkingLot() {}
    
    public ParkingLot(String location, String lotNumber, ParkingLotType type, Integer totalSpots) {
        this.location = location;
        this.lotNumber = lotNumber;
        this.type = type;
        this.totalSpots = totalSpots;
    }
    
    // Helper method to add parking spot
    public void addParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
        parkingSpot.setParkingLot(this);
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    
    public Integer getTotalSpots() { return totalSpots; }
    public void setTotalSpots(Integer totalSpots) { this.totalSpots = totalSpots; }
    
    public ParkingLotType getType() { return type; }
    public void setType(ParkingLotType type) { this.type = type; }
    
    public List<String> getFacilities() { return facilities; }
    public void setFacilities(List<String> facilities) { this.facilities = facilities; }
    
    public List<ParkingSpot> getParkingSpots() { return parkingSpots; }
    public void setParkingSpots(List<ParkingSpot> parkingSpots) { this.parkingSpots = parkingSpots; }
}