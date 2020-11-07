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
    private Boolean tipo_perfil;

    public Notificacion(){}
    public Notificacion(Integer id, Integer perfil_id, String mensaje, Boolean tipo_perfil) {
        this.id = id;
        this.perfil_id = perfil_id;
        this.mensaje = mensaje;
        this.tipo_perfil = tipo_perfil;
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

    public Boolean getTipo_perfil() {
        return tipo_perfil;
    }

    public void setTipo_perfil(Boolean tipo_perfil) {
        this.tipo_perfil = tipo_perfil;
    }
}
