package edu.co.icesi.examjpatemplate.service;

import edu.co.icesi.examjpatemplate.entity.GeoPoint;

import java.util.List;
import java.util.Optional;

public interface GeoPointService {
    public List<GeoPoint> getGeoPointsFromRoute(String routeName);
    public Optional<GeoPoint> getBusLastLocation(String licensePlate);
}
