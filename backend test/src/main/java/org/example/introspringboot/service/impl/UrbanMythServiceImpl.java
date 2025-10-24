package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.dto.UrbanMythRequest;
import org.example.introspringboot.api.dto.UrbanMythResponse;
import org.example.introspringboot.api.mappers.UrbanMythMapper;
import org.example.introspringboot.entity.UrbanMyth;
import org.example.introspringboot.repository.UrbanMythRepository;
import org.example.introspringboot.service.UrbanMythService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrbanMythServiceImpl implements UrbanMythService {
    @Autowired
    private UrbanMythRepository urbanMythRepository;

    @Autowired
    private UrbanMythMapper urbanMythMapper;

    @Override
    public void createUrbanMyth(UrbanMythRequest urbanMythDTO) {
        var urbanMyth = urbanMythMapper.toEntity(urbanMythDTO);
        urbanMythRepository.save(urbanMyth);
    }

    @Override
    public List<UrbanMythResponse> getAllUrbanMyths() {
        return urbanMythRepository.findAll().stream().map(
                urbanMythMapper::toDto
        ).toList();
    }
}
