package org.example.repo;

import org.example.models.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookingRepo {

    Map<String, Trip> bookingMap = new ConcurrentHashMap<>();

    Map<String, List<Trip>> driverBookingHistory = new ConcurrentHashMap<>();

    Map<String, List<Trip>> riderBookingHistory = new ConcurrentHashMap<>();

    public void addOrUpdateBooking(Trip trip) {
        bookingMap.put(trip.getTripId(), trip);
        List<Trip> driverTrips = driverBookingHistory.getOrDefault(trip.getDriverId(), new ArrayList<>());
        driverTrips.add(trip);
        driverBookingHistory.put(trip.getDriverId(), driverTrips);
        List<Trip> riderTrips = riderBookingHistory.getOrDefault(trip.getRiderId(), new ArrayList<>());
        riderTrips.add(trip);
        riderBookingHistory.put(trip.getRiderId(), riderTrips);
    }

    public Trip getTrip(String tripId) {
        if (bookingMap.containsKey(tripId)) {
            return bookingMap.get(tripId);
        }
        return null;
    }

    public List<Trip> getDriverTrips(String driverId){
        return driverBookingHistory.get(driverId);
    }

    public List<Trip> getRiderTrips(String riderId){
        return riderBookingHistory.get(riderId);
    }
}
