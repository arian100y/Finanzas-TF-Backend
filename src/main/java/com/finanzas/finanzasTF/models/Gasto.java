package com.finanzas.finanzasTF.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer deuda_id;
    private String descripcion;
    private Float monto;
    private Float envioMonto;
    private LocalDateTime fecha;

    public Gasto(){}
    public Gasto(Integer id, Integer deuda_id, String descripcion, Float monto, Float envioMonto, LocalDateTime fecha) {
        this.id = id;
        this.deuda_id = deuda_id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.envioMonto = envioMonto;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeuda_id() {
        return deuda_id;
    }

    public void setDeuda_id(Integer deuda_id) {
        this.deuda_id = deuda_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getEnvioMonto() {
        return envioMonto;
    }

    public void setEnvioMonto(Float envioMonto) {
        this.envioMonto = envioMonto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
