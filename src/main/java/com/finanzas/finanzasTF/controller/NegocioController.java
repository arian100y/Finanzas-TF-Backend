package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.service.ClienteService;
import com.finanzas.finanzasTF.service.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/negocios")
public class NegocioController {


    @Autowired
    private final NegocioService negocioService;

    public NegocioController(NegocioService negocioService){
        this.negocioService = negocioService;
    }

    @GetMapping
    public List<Negocio> getAllNegocios(){
        return negocioService.getAllNegocios();
    }

    @PostMapping
    public void addNegocio(@RequestBody Negocio negocio){
        System.out.println(negocio.getPerfil().getCorreo());
        negocioService.addNegocio(negocio);
    }
}
