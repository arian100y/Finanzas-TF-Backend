package com.finanzas.finanzasTF.models;

import javax.persistence.*;

@Entity
@Table(name = "tasas")
public class Tasa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer tipo;
    private Float monto;
    private Integer periodo;

    public Tasa(Integer id, Integer tipo, Float monto, Integer periodo) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.periodo = periodo;
    }
    public Tasa(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
}
