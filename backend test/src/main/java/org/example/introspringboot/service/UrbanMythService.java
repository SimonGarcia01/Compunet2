package org.example.introspringboot.service;

import org.example.introspringboot.api.dto.UrbanMythRequest;
import org.example.introspringboot.api.dto.UrbanMythResponse;

import java.util.List;

public interface UrbanMythService {
    void createUrbanMyth(UrbanMythRequest urbanMyth);
    List<UrbanMythResponse> getAllUrbanMyths();
}
