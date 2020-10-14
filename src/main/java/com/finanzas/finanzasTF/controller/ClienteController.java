package com.finanzas.finanzasTF.controller;

import ch.qos.logback.core.net.server.Client;
import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.Notification;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getAllClientes(){
        return clienteService.getAllClientes();
    }

    @PostMapping
    public void addCliente(@RequestBody Cliente cliente){
        clienteService.addCliente(cliente);
    }


}
