package org.example.introspringboot.api.dto;

import org.example.introspringboot.entity.Domicilio.EstadoDomicilio;

public class DomicilioRequest {
    
    private String nombreDomiciliario;
    private EstadoDomicilio estado;
    private Long userId;

    public DomicilioRequest() {}

    public DomicilioRequest(String nombreDomiciliario, EstadoDomicilio estado, Long userId) {
        this.nombreDomiciliario = nombreDomiciliario;
        this.estado = estado;
        this.userId = userId;
    }

    public String getNombreDomiciliario() {
        return nombreDomiciliario;
    }

    public void setNombreDomiciliario(String nombreDomiciliario) {
        this.nombreDomiciliario = nombreDomiciliario;
    }

    public EstadoDomicilio getEstado() {
        return estado;
    }

    public void setEstado(EstadoDomicilio estado) {
        this.estado = estado;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
