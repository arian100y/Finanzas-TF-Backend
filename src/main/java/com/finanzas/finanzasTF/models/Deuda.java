package com.finanzas.finanzasTF.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deudas")
public class Deuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cliente_id;
    private LocalDateTime fecha;
    private Float monto;
    private Float interes;
    private Boolean metodoPago;
    private Boolean pagado;
    @OneToMany
    @JoinColumn(name="deuda_id")
    private List<Gasto> gastos;
    public Deuda(){
    }

    public Deuda(Integer id, Integer cliente_id,
                 LocalDateTime fecha, Float monto, Float interes, Boolean metodoPago, Boolean pagado) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.fecha = fecha;
        this.monto = monto;
        this.interes = interes;
        this.metodoPago = metodoPago;
        this.pagado = pagado;
        this.gastos = new ArrayList<>();
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getInteres() {
        return interes;
    }

    public void setInteres(Float interes) {
        this.interes = interes;
    }

    public Boolean getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Boolean metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }
}
