package com.finanzas.finanzasTF.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="negocios")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Perfil perfil;
    private Long RUC;
    private String password;
    private String codigo;
    @OneToMany
    @JoinColumn(name="negocio_id")
    private List<Cliente> clientes;

    public Negocio(){}
    public Negocio(Integer id, Perfil perfil, Long RUC, String password, String codigo) {
        this.id = id;
        this.perfil = perfil;
        this.RUC = RUC;
        this.password = password;
        this.codigo = codigo;
        clientes = new ArrayList<>();

    }

    public Integer getId() {
        return id;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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

    public Long getRUC() {
        return RUC;
    }

    public void setRUC(Long RUC) {
        this.RUC = RUC;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
