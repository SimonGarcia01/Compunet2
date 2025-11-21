package org.example.introspringboot.api.dto;

import org.example.introspringboot.entity.Domicilio.EstadoDomicilio;

public class DomicilioResponse {
    
    private Long id;
    private String nombreDomiciliario;
    private EstadoDomicilio estado;
    private Long userId;
    private String username;

    public DomicilioResponse() {}

    public DomicilioResponse(Long id, String nombreDomiciliario, EstadoDomicilio estado, Long userId, String username) {
        this.id = id;
        this.nombreDomiciliario = nombreDomiciliario;
        this.estado = estado;
        this.userId = userId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
