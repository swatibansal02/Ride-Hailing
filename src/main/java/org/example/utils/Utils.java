package org.example.utils;

import lombok.experimental.UtilityClass;
import org.example.models.Location;

@UtilityClass
public class Utils {

    public double getDistance(Location fromLocation, Location toLocation) {
        double x = fromLocation.getX() - toLocation.getX(); //4 - 2
        double y = fromLocation.getY() - toLocation.getY(); //4 - 2
        double radius = Math.pow(x, 2) + Math.pow(y, 2); // 4+4
        return Math.sqrt(radius);
    }
}
