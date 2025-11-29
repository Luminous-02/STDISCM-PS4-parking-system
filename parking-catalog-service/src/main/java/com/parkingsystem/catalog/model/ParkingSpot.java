package com.parkingsystem.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_spots", schema = "catalog")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "spot_number", nullable = false)
    private String spotNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingSpotType type;
    
    @Column(name = "position_description")
    private String positionDescription; // "near entrance", "far corner", "elevator access"
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;
    
    @Column(nullable = false)
    private Boolean available = true;
    
    // Constructors
    public ParkingSpot() {}
    
    public ParkingSpot(String spotNumber, ParkingLot parkingLot, ParkingSpotType type, String positionDescription) {
        this.spotNumber = spotNumber;
        this.parkingLot = parkingLot;
        this.type = type;
        this.positionDescription = positionDescription;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSpotNumber() { return spotNumber; }
    public void setSpotNumber(String spotNumber) { this.spotNumber = spotNumber; }
    
    public ParkingSpotType getType() { return type; }
    public void setType(ParkingSpotType type) { this.type = type; }
    
    public String getPositionDescription() { return positionDescription; }
    public void setPositionDescription(String positionDescription) { this.positionDescription = positionDescription; }
    
    public ParkingLot getParkingLot() { return parkingLot; }
    public void setParkingLot(ParkingLot parkingLot) { this.parkingLot = parkingLot; }
    
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}