package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService  {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    public List<Cliente> getAllClientes(){

        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);

        return clientes;
    }

    public void addCliente(Cliente cliente){
    perfilRepository.save(cliente.getPerfil());
    clienteRepository.save(cliente);
    }


}
