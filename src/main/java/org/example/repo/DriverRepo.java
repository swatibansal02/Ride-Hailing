package org.example.repo;

import org.example.exceptions.DriverAlreadyExists;
import org.example.models.Driver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverRepo {

    private static final Map<String, Driver> DriverHashMap = new ConcurrentHashMap<>();

    public void createDriver(Driver driver){
        if(DriverHashMap.containsKey(driver.getId())){
            throw new DriverAlreadyExists();
        }
        DriverHashMap.put(driver.getId(), driver);
    }

    public Driver getDriver(String driverId){
        return DriverHashMap.get(driverId);
    }

    public void updateDriver(Driver driver){
        DriverHashMap.put(driver.getId(), driver);
    }

}
