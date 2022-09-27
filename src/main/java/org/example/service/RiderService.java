package org.example.service;

import org.example.models.Rider;

public interface RiderService {

    void register(Rider rider);

    Rider getRider(String riderId);
}
