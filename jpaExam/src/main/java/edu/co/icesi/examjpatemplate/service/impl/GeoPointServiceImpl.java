package edu.co.icesi.examjpatemplate.service.impl;

import edu.co.icesi.examjpatemplate.entity.GeoPoint;
import edu.co.icesi.examjpatemplate.repository.GeoPointRepository;
import edu.co.icesi.examjpatemplate.service.GeoPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeoPointServiceImpl implements GeoPointService {

    @Autowired
    private GeoPointRepository geoPointRepository;

    @Override
    public List<GeoPoint> getGeoPointsFromRoute(String routeName) {
        return geoPointRepository.findByBus_Route_RouteName(routeName);
    }

    @Override
    public Optional<GeoPoint> getBusLastLocation(String licensePlate) {
        return geoPointRepository.findFirstByBus_LicensePlateOrderByTimestampDesc(licensePlate);
    }
}
