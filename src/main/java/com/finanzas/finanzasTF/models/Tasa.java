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
    private Integer periodoCapitalizacion;

    public Tasa(Integer id, Integer tipo, Float monto, Integer periodo, Integer periodoCapitalizacion) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.periodo = periodo;
        this.periodoCapitalizacion = periodoCapitalizacion;
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

    public Integer getPeriodoCapitalizacion() {
        return periodoCapitalizacion;
    }


    public void setPeriodoCapitalizacion(Integer periodoCapitalizacion) {
        this.periodoCapitalizacion = periodoCapitalizacion;
    }
}
