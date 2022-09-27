package org.example.repo;

import org.example.exceptions.RiderAlreadyExists;
import org.example.models.Rider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RiderRepo {

    private static final Map<String, Rider> riderHashMap = new ConcurrentHashMap<>();

    public void createRider(Rider rider){
        if(riderHashMap.containsKey(rider.getId())){
            throw new RiderAlreadyExists();
        }
        riderHashMap.put(rider.getId(), rider);
    }

    public void updateRider(Rider rider){
        riderHashMap.put(rider.getId(), rider);
    }

    public Rider getRider(String id){
        return riderHashMap.get(id);
    }

}
