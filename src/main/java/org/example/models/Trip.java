package org.example.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Trip {

    private String tripId;
    private String riderId;
    private String cabId; //vehicleNumber
    private String driverId;
    private TripStatus tripStatus;
    private Location fromPoint;
    private Location toPoint;
    private Double distance;
    private TripInvoice invoice;

    public void setTripId(){
      this.tripId = UUID.randomUUID().toString();
    }
}
