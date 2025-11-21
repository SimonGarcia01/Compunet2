package org.example.introspringboot.api;

import org.example.introspringboot.api.dto.DomicilioRequest;
import org.example.introspringboot.api.dto.DomicilioResponse;
import org.example.introspringboot.api.dto.MessageResponse;
import org.example.introspringboot.service.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/domicilios")
public class DomicilioRestController {

    @Autowired
    private DomicilioService domicilioService;

    @PostMapping
    public ResponseEntity<?> createDomicilio(@RequestBody DomicilioRequest domicilioRequest) {
        try {
            DomicilioResponse response = domicilioService.createDomicilio(domicilioRequest);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                    new MessageResponse(e.getMessage())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDomicilio(@PathVariable Long id, 
                                             @RequestBody DomicilioRequest domicilioRequest) {
        try {
            DomicilioResponse response = domicilioService.updateDomicilio(id, domicilioRequest);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                    new MessageResponse(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDomicilio(@PathVariable Long id) {
        try {
            domicilioService.deleteDomicilio(id);
            return ResponseEntity.status(200).body(
                    new MessageResponse("Domicilio eliminado exitosamente")
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                    new MessageResponse(e.getMessage())
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDomicilios() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<DomicilioResponse> domicilios = domicilioService.getDomiciliosByUsername(username);
            return ResponseEntity.status(200).body(domicilios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new MessageResponse(e.getMessage())
            );
        }
    }
}
