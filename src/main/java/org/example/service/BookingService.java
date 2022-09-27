package org.example.service;

import org.example.models.CabType;
import org.example.models.Location;
import org.example.models.Trip;

import java.util.List;

public interface BookingService {

    Trip createTrip(String riderId, CabType cabType, Location fromLocation, Location toLocation, Double couponDiscount);

    Trip endTrip(String tripId);

    List<Trip> getRideHistory(String riderId);

    List<Trip> driverBookingHistory(String driverId);
}
