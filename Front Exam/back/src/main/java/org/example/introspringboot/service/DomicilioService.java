package org.example.introspringboot.service;

import org.example.introspringboot.api.dto.DomicilioRequest;
import org.example.introspringboot.api.dto.DomicilioResponse;

import java.util.List;

public interface DomicilioService {
    DomicilioResponse createDomicilio(DomicilioRequest domicilioRequest);
    DomicilioResponse updateDomicilio(Long id, DomicilioRequest domicilioRequest);
    void deleteDomicilio(Long id);
    List<DomicilioResponse> getAllDomicilios();
    List<DomicilioResponse> getDomiciliosByUsername(String username);
}
