package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.repository.DeudaRepository;
import com.finanzas.finanzasTF.service.ClienteService;
import com.finanzas.finanzasTF.service.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/deudas")
public class DeudaController {
    @Autowired
    private final DeudaService deudadService;
    @Autowired
    private final ClienteService clienteService;
    @Autowired
    private DeudaRepository deudaRepository;
    public DeudaController(DeudaService deudadService, ClienteService clienteService){
        this.deudadService = deudadService;
        this.clienteService = clienteService;
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
    @DeleteMapping
    public void deleteAll(){
        deudaRepository.deleteAll();
    }

    @PutMapping
    public void makePagado(@RequestBody Deuda deuda){
        deudadService.putDeuda(deuda);
    }
    @GetMapping("/check")
    public void checkManuallyDeudas(){
        deudadService.checkDeudas();
    }

    //@Scheduled(cron = "0 0 * * *")
    @GetMapping("/test")
    public void checkDeudas(){
        deudadService.checkInteres();
        clienteService.checkMantenimiento(null);
        clienteService.checkMora();
    }

    @PostMapping("/generate")
    public void generate(@RequestBody LocalDateTime date){
        deudadService.generateInterest(date);
        //clienteService.simulateMantenimiento();
    }

    @GetMapping("/mora")
    public void generateMora(){
        //generate();
        clienteService.simulateMora();
    }
}
