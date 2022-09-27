package org.example.service;

import org.example.models.Driver;

public interface DriverService {

    void registerDriver(Driver driver);

    Driver getDriver(String id);
}
