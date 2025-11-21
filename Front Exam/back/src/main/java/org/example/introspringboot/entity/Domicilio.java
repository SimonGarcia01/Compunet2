package org.example.introspringboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "domicilios")
public class Domicilio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreDomiciliario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoDomicilio estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum EstadoDomicilio {
        EN_CAMINO("En camino"),
        EN_REPARTO("En reparto"),
        ENTREGADO("Entregado");

        private final String displayName;

        EstadoDomicilio(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public Domicilio() {}

    public Domicilio(String nombreDomiciliario, EstadoDomicilio estado, User user) {
        this.nombreDomiciliario = nombreDomiciliario;
        this.estado = estado;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
