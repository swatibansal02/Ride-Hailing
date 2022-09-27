package org.example.core;

import org.example.exceptions.NoCabAvailableException;
import org.example.models.Cab;
import org.example.models.CabType;
import org.example.models.Location;

public interface CabFindingStrategy {

    Cab assignCab(Location riderLocation, CabType cabType, double distance) throws NoCabAvailableException;
}
