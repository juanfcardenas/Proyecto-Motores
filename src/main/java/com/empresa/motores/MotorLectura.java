package com.empresa.motores;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "lecturas_motores")
public class MotorLectura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre o código del motor es obligatorio")
    private String nombreMotor;

    @NotNull(message = "El voltaje es obligatorio")
    @PositiveOrZero(message = "El voltaje no puede ser negativo")
    private Double voltaje;

    @NotNull(message = "El amperaje es obligatorio")
    @PositiveOrZero(message = "El amperaje no puede ser negativo")
    private Double amperaje;

    private Double potencia; // Watts (W)

    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.potencia = this.voltaje * this.amperaje;
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreMotor() { return nombreMotor; }
    public void setNombreMotor(String nombreMotor) { this.nombreMotor = nombreMotor; }

    public Double getVoltaje() { return voltaje; }
    public void setVoltaje(Double voltaje) { this.voltaje = voltaje; }

    public Double getAmperaje() { return amperaje; }
    public void setAmperaje(Double amperaje) { this.amperaje = amperaje; }

    public Double getPotencia() { return potencia; }
    public void setPotencia(Double potencia) { this.potencia = potencia; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
