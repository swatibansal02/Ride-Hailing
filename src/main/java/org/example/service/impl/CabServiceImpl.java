package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.core.CabFindingStrategy;
import org.example.exceptions.NoCabAvailableException;
import org.example.models.Cab;
import org.example.models.CabType;
import org.example.models.Location;
import org.example.repo.CabRepo;
import org.example.service.CabService;


@AllArgsConstructor
public class CabServiceImpl implements CabService {

    private CabRepo cabRepo;

    private CabFindingStrategy cabFindingStrategy;

    @Override
    public void registerNewCab(Cab cab) {
        cabRepo.createOrUpdateCab(cab);
    }

    @Override
    public Cab getAvailableCab(Location riderLocation, CabType cabType, double distance) {
        try {
            Cab assignedCab = cabFindingStrategy.assignCab(riderLocation, cabType, distance);
            assignedCab.setAvailable(false);
            cabRepo.createOrUpdateCab(assignedCab);
            return assignedCab;
        } catch (NoCabAvailableException noCabAvailableException) {
            throw noCabAvailableException;
        }
    }

    @Override
    public void updateCabAvailability(String cabId, boolean isAvailable) {
        Cab cab = cabRepo.getCab(cabId);
        cab.setAvailable(isAvailable);
        cabRepo.createOrUpdateCab(cab);
    }

    @Override
    public Cab getCab(String cabId) {
        return cabRepo.getCab(cabId);
    }

    @Override
    public void updateCabLocationAndAvailability(String cabId, boolean isAvailable, Location newLocation) {
        Cab cab = getCab(cabId);
        cab.setCurrentLocation(newLocation);
        cab.setAvailable(isAvailable);
        cabRepo.createOrUpdateCab(cab);
    }
}
