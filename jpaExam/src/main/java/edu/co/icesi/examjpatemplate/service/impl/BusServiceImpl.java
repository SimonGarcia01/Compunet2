package edu.co.icesi.examjpatemplate.service.impl;

import edu.co.icesi.examjpatemplate.entity.Bus;
import edu.co.icesi.examjpatemplate.repository.BusRepository;
import edu.co.icesi.examjpatemplate.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepository;

    @Override
    public List<Bus> getBusesRouteType(String route) {
        return busRepository.findByRoute_Type(route);
    }
}
