package com.finanzas.finanzasTF.models;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private Perfil perfil;
    @OneToOne(cascade = CascadeType.ALL)
    private Tasa tasa;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="cliente_id")
    private List<Deuda> deudas;

    private String credito;
    private Boolean mantenimiento;
    private LocalDateTime fechaEmision;
    private String moneda;
    private Integer capitalizacion;

    private Integer negocio_id;

    public Integer getNegocio_id() {
        return negocio_id;
    }

    public void setNegocio_id(Integer negocio_id) {
        this.negocio_id = negocio_id;
    }

    public Cliente(){

    }
    public Cliente(Integer negocio_id, Integer id, Perfil perfil, String credito,
                   Boolean mantenimiento, LocalDateTime fechaEmision, String moneda, Tasa tasa) {
        this.id = id;
        this.perfil = perfil;
        this.credito = credito;
        this.mantenimiento = mantenimiento;
        this.fechaEmision = fechaEmision;
        this.negocio_id = negocio_id;
        this.moneda = moneda;
        this.deudas = new ArrayList<>();
        this.tasa = tasa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Tasa getTasa() {
        return tasa;
    }

    public void setTasa(Tasa tasa) {
        this.tasa = tasa;
    }

    public List<Deuda> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<Deuda> deudas) {
        this.deudas = deudas;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public Boolean getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Boolean mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
