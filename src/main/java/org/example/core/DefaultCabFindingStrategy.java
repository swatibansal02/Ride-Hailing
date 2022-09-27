package org.example.core;

import org.example.exceptions.NoCabAvailableException;
import org.example.models.Cab;
import org.example.models.CabType;
import org.example.models.Location;
import org.example.repo.CabRepo;
import org.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.utils.Constants.RADIUS;

public class DefaultCabFindingStrategy implements CabFindingStrategy {

    private static final CabRepo cabRepo = new CabRepo();

    public Cab assignCab(Location riderLocation, CabType cabType, double distance) throws NoCabAvailableException {
        //10km
        List<Cab> nearByCabs = findCabsWithInRadius(riderLocation, distance);
        if (nearByCabs.isEmpty()) {
            throw new NoCabAvailableException();
        }
        //checking for required cab type
        if (Objects.nonNull(cabType)) {
            for (Cab nearByCab : nearByCabs) {
                if (cabType.equals(nearByCab.getCabType())) {
                    return nearByCab;
                }
            }
        }
        //as required cab type is not available assigning whatever type of cab is available
        return nearByCabs.get(0); //
    }

    private List<Cab> findCabsWithInRadius(Location riderLocation, double distance) {
        List<Cab> regionCabs = cabRepo.getRegionCabs(riderLocation.getGridId());
        List<Cab> availableCabs = new ArrayList();
        for (Cab cab : regionCabs) {
            if (cab.isAvailable() && distance <= RADIUS) {
                availableCabs.add(cab);
            }
        }
        sortCabsByDistance(availableCabs, riderLocation);
        return availableCabs;
    }

    private void sortCabsByDistance(List<Cab> nearByCabs, Location riderLocation) {
        nearByCabs.sort((o1, o2) -> {
            double o1distance = Utils.getDistance(o1.getCurrentLocation(), riderLocation);
            double o2distance = Utils.getDistance(o2.getCurrentLocation(), riderLocation);
            if (o1distance == o2distance) {
                return 0;
            } else if (o1distance > o2distance) {
                return 1;
            }
            return -1;
        });
    }

}
