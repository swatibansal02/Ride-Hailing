package org.example.repo;

import org.example.models.Cab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CabRepo {

    Map<String, Cab> cabMap = new ConcurrentHashMap<>();

    //region based
    private static  final Map<String, List<Cab>> regionBasedCabMap = new ConcurrentHashMap<>();

    public void createOrUpdateCab(Cab cab){
          cabMap.put(cab.getVehicleNumber(), cab);
          String gridId = cab.getCurrentLocation().getGridId(); //region key
          List<Cab> regionalCabs = regionBasedCabMap.getOrDefault(gridId, new ArrayList<>());
          regionalCabs.add(cab);
          regionBasedCabMap.put(gridId, regionalCabs);
    }

    public List<Cab> getRegionCabs(String gridId){
        return regionBasedCabMap.get(gridId);
    }

    public Cab getCab(String cabId){
        return cabMap.get(cabId);
    }

}
