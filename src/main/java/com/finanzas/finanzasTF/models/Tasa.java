package com.finanzas.finanzasTF.models;

import javax.persistence.*;

@Entity
@Table(name = "tasas")
public class Tasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tipo;
    private Float monto;
    private Integer periodo;


}
