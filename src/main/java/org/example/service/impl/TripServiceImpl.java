package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.core.PricingStrategy;
import org.example.exceptions.TripNotFoundException;
import org.example.models.Cab;
import org.example.models.CabType;
import org.example.models.Location;
import org.example.models.Trip;
import org.example.models.TripInvoice;
import org.example.models.TripStatus;
import org.example.repo.BookingRepo;
import org.example.service.BookingService;
import org.example.service.CabService;
import org.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class TripServiceImpl implements BookingService {

    private CabService cabService;
    private PricingStrategy pricingStrategy; //Price
    private BookingRepo bookingRepo;

    @Override
    public Trip createTrip(String riderId, CabType cabType, Location fromLocation, Location toLocation, Double couponDiscount) {
        Trip trip = null;
        double distance = Utils.getDistance(fromLocation, toLocation);
        Cab cab = cabService.getAvailableCab(fromLocation, cabType, distance);
        if (Objects.nonNull(cab)) {
            trip = Trip.builder()
                    .riderId(riderId)
                    .tripStatus(TripStatus.BOOKED)
                    .toPoint(toLocation)
                    .fromPoint(fromLocation)
                    .cabId(cab.getVehicleNumber())
                    .distance(distance)
                    .invoice(getPrice(couponDiscount, fromLocation, toLocation))
                    .driverId(cab.getDriverId())
                    .build();
            trip.setTripId();
            bookingRepo.addOrUpdateBooking(trip);
            cabService.updateCabAvailability(cab.getVehicleNumber(), false);
        } else {
            System.out.println("No cab is available please try after sometime");
        }
        return trip;
    }

    @Override
    public Trip endTrip(String tripId) {
        Trip onGoingTrip = bookingRepo.getTrip(tripId);
        if (Objects.isNull(onGoingTrip)) {
            throw new TripNotFoundException();
        }
        onGoingTrip.setTripStatus(TripStatus.FINISHED);
        bookingRepo.addOrUpdateBooking(onGoingTrip);
        cabService.updateCabLocationAndAvailability(onGoingTrip.getCabId(), true, onGoingTrip.getToPoint());
        return onGoingTrip;
    }

    private TripInvoice getPrice(Double couponDiscount, Location fromLocation, Location toLocation) {
        double distance = Utils.getDistance(fromLocation, toLocation);
        double totalPrice = pricingStrategy.calculatePrice(distance);
        return TripInvoice.builder()
                .discount(couponDiscount)
                .totalPrice(totalPrice)
                .priceAfterDiscount(totalPrice - couponDiscount)
                .build();
    }

    @Override
    public List<Trip> getRideHistory(String riderId) {
        List<Trip> trips = bookingRepo.getRiderTrips(riderId);
        if (Objects.isNull(trips)) {
            return new ArrayList<>();
        }
        return trips;
    }

    @Override
    public List<Trip> driverBookingHistory(String driverId) {
        List<Trip> trips = bookingRepo.getDriverTrips(driverId);
        if (Objects.isNull(trips)) {
            return new ArrayList<>();
        }
        return trips;
    }
}
