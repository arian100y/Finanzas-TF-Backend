package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Tasa;
import com.finanzas.finanzasTF.service.ClienteService;
import com.finanzas.finanzasTF.service.TasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasas")
public class TasaController {

    @Autowired
    private final TasaService tasaService;
    public TasaController(TasaService tasaService){
        this.tasaService = tasaService;
    }

    @GetMapping
    public List<Tasa> getAllTasas(){
        return tasaService.getAllTasas();
    }

    @PostMapping
    public void addTasa(@RequestBody Tasa tasa){
        tasaService.addTasa(tasa);
    }

}
