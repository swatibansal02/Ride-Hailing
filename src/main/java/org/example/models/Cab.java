package org.example.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Cab {

    private String vehicleNumber;
    private Location currentLocation;
    private boolean isAvailable;
    private String driverId;
    private CabType cabType;
}
