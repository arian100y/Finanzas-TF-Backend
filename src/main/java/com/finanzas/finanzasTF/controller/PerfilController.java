package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.models.Perfil;
import com.finanzas.finanzasTF.service.NegocioService;
import com.finanzas.finanzasTF.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {


    @Autowired
    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService){
        this.perfilService = perfilService;
    }

    @GetMapping
    public List<Perfil> getAllPerfiles(){
        return perfilService.getAllPerfiles();
    }

    @PostMapping
    public void addPerfil(@RequestBody Perfil perfil){
        perfilService.addPerfil(perfil);
    }

    
}
