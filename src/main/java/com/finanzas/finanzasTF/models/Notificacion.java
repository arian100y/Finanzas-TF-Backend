package com.finanzas.finanzasTF.models;

import javax.persistence.*;

@Entity
@Table(name="notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer perfil_id;
    private String mensaje;
    private Boolean estado;

    public Notificacion(){}
    public Notificacion(Integer id, Integer perfil_id, String mensaje, Boolean estado) {
        this.id = id;
        this.perfil_id = perfil_id;
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(Integer perfil_id) {
        this.perfil_id = perfil_id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
