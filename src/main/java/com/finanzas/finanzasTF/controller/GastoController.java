package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Gasto;
import com.finanzas.finanzasTF.models.Notificacion;
import com.finanzas.finanzasTF.service.GastoService;
import com.finanzas.finanzasTF.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {
    @Autowired
    private final GastoService gastoService;
    public GastoController(GastoService gastoService){
        this.gastoService = gastoService;
    }

    @GetMapping
    public List<Gasto> getAllGastos(){
        return gastoService.getAllGastos();
    }

    @PostMapping
    public void addGasto(@RequestBody Gasto gasto){
        gastoService.addGasto(gasto);
    }

    @GetMapping("/{id}")
    public List<Gasto> getByUserId(@PathVariable Integer id){
        return gastoService.getAllByUserId(id);
    }

    @DeleteMapping
    public void deleteALl(){
        gastoService.deleteAll();
    }
}
