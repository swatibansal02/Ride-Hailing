package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.models.Cab;
import org.example.models.Driver;
import org.example.repo.DriverRepo;
import org.example.service.CabService;
import org.example.service.DriverService;

@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private DriverRepo driverRepo;

    private CabService cabService;

    @Override
    public void registerDriver(Driver driver) {
        driver.setId();
        driverRepo.createDriver(driver);
        Cab cab = driver.getCab();
        cab.setDriverId(driver.getId());
        cabService.registerNewCab(cab);
    }

    @Override
    public Driver getDriver(String id) {
        return driverRepo.getDriver(id);
    }

}
