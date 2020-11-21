package com.finanzas.finanzasTF.controller;

import ch.qos.logback.core.net.server.Client;
import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/verify")
    public ResponseEntity<?>  verify(@RequestBody Cliente cliente){

        return clienteService.verify(cliente);
    }
    @PostMapping
    public void addCliente(@RequestBody Cliente cliente){

         clienteService.addCliente(cliente);
    }

    @PutMapping("/edit")
    public void editCliente(@RequestBody Cliente cliente){
        clienteService.editCliente(cliente);
    }

    @PostMapping("/delete")
    public void deleteAll(){
        clienteService.delete();
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        clienteService.deleteById(id);
    }
    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody Negocio negocio){
        Cliente temp = clienteService.verifyLogin(negocio);
        if(temp!= null){
            return new ResponseEntity<>(temp, HttpStatus.OK);
        }else{
            String response = clienteService.checkWhatIsWrong(negocio);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/DNI={DNI}")
    public Cliente getClienteByDNI(@PathVariable("DNI") Integer DNI){
        return clienteService.getClienteByDNI(DNI);
    }

    @GetMapping("/perfil_id={perfil_id}")
    public Cliente getClienteByPerfil_id(@PathVariable("perfil_id") Integer perfil_id){
        return clienteService.getClienteByPerfil_id(perfil_id);
    }

}
