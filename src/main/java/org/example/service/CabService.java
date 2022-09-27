package org.example.service;

import org.example.models.Cab;

import org.example.models.CabType;
import org.example.models.Location;

public interface CabService {

    void registerNewCab(Cab cab);

    Cab getAvailableCab(Location riderLocation, CabType cabType, double distance);

    void updateCabAvailability(String cabId, boolean isAvailable);

    Cab getCab(String cabId);

    void updateCabLocationAndAvailability(String cabId, boolean isAvailable, Location newLocation);
}
