package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.models.Rider;
import org.example.repo.RiderRepo;
import org.example.service.RiderService;

@AllArgsConstructor
public class RiderServiceImpl implements RiderService {

    private RiderRepo riderRepo;

    @Override
    public void register(Rider rider) {
        rider.setId();
        riderRepo.createRider(rider);
    }

    @Override
    public Rider getRider(String riderId){
        return riderRepo.getRider(riderId);
    }
}
