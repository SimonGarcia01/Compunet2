package edu.co.icesi.examjpatemplate.controller;

import edu.co.icesi.examjpatemplate.entity.Bus;
import edu.co.icesi.examjpatemplate.entity.GeoPoint;
import edu.co.icesi.examjpatemplate.entity.Route;
import edu.co.icesi.examjpatemplate.service.BusService;
import edu.co.icesi.examjpatemplate.service.GeoPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    @Qualifier("busServiceImpl")
    private BusService busService;

    @Autowired
    @Qualifier("geoPointServiceImpl")
    private GeoPointService geoPointService;

    @GetMapping("1")
    public ResponseEntity<?> query1(){
        List<Bus> output = busService.getBusesRouteType("Alimentador");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("2")
    public ResponseEntity<?> query2(){
        List<GeoPoint> output = geoPointService.getGeoPointsFromRoute("T31");
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("3")
    public ResponseEntity<?> query3(){
        Optional<GeoPoint> output = geoPointService.getBusLastLocation("JJJ000");
        return ResponseEntity.status(200).body(output);
    }
}
