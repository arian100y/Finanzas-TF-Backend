package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.service.ClienteService;
import com.finanzas.finanzasTF.service.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deudas")
public class DeudaController {
    @Autowired
    private final DeudaService deudadService;
    public DeudaController(DeudaService deudadService){
        this.deudadService = deudadService;
    }

    @GetMapping
    public List<Deuda> getAllDeudas(){
        return deudadService.getAllDeudas();
    }

    @PostMapping
    public void addDeuda(@RequestBody Deuda deuda){
        deudadService.addDeuda(deuda);
    }

    @GetMapping("/porfecha/{id}")
    Deuda getDeudaFechas(@PathVariable Integer id){
        return deudadService.getDeudaFecha(id);
    }
}
