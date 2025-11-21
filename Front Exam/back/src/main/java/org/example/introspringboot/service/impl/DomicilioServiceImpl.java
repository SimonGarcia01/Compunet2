package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.dto.DomicilioRequest;
import org.example.introspringboot.api.dto.DomicilioResponse;
import org.example.introspringboot.entity.Domicilio;
import org.example.introspringboot.entity.User;
import org.example.introspringboot.repository.DomicilioRepository;
import org.example.introspringboot.repository.UserRepository;
import org.example.introspringboot.service.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomicilioServiceImpl implements DomicilioService {

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DomicilioResponse createDomicilio(DomicilioRequest domicilioRequest) {
        Optional<User> userOpt = userRepository.findById(domicilioRequest.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con id: " + domicilioRequest.getUserId());
        }

        User user = userOpt.get();
        Domicilio domicilio = new Domicilio();
        domicilio.setNombreDomiciliario(domicilioRequest.getNombreDomiciliario());
        domicilio.setEstado(domicilioRequest.getEstado());
        domicilio.setUser(user);

        Domicilio savedDomicilio = domicilioRepository.save(domicilio);
        return mapToResponse(savedDomicilio);
    }

    @Override
    public DomicilioResponse updateDomicilio(Long id, DomicilioRequest domicilioRequest) {
        Optional<Domicilio> domicilioOpt = domicilioRepository.findById(id);
        if (domicilioOpt.isEmpty()) {
            throw new RuntimeException("Domicilio no encontrado con id: " + id);
        }

        Domicilio domicilio = domicilioOpt.get();
        domicilio.setNombreDomiciliario(domicilioRequest.getNombreDomiciliario());
        domicilio.setEstado(domicilioRequest.getEstado());

        Domicilio updatedDomicilio = domicilioRepository.save(domicilio);
        return mapToResponse(updatedDomicilio);
    }

    @Override
    public void deleteDomicilio(Long id) {
        Optional<Domicilio> domicilioOpt = domicilioRepository.findById(id);
        if (domicilioOpt.isEmpty()) {
            throw new RuntimeException("Domicilio no encontrado con id: " + id);
        }
        domicilioRepository.deleteById(id);
    }

    @Override
    public List<DomicilioResponse> getAllDomicilios() {
        return domicilioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DomicilioResponse> getDomiciliosByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado: " + username);
        }
        return domicilioRepository.findByUserId(userOpt.get().getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DomicilioResponse mapToResponse(Domicilio domicilio) {
        return new DomicilioResponse(
                domicilio.getId(),
                domicilio.getNombreDomiciliario(),
                domicilio.getEstado(),
                domicilio.getUser().getId(),
                domicilio.getUser().getUsername()
        );
    }
}
