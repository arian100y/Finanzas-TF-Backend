package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.NegocioRepository;
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
    @Autowired
    private NegocioService NegocioService;

    public List<Cliente> getAllClientes(){

        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);

        return clientes;
    }

    public void addCliente(Cliente cliente){
    perfilRepository.save(cliente.getPerfil());
    clienteRepository.save(cliente);
    }

    public Cliente verifyLogin(Negocio negocio){

        Negocio nego = NegocioService.getNegocioByCodigo(negocio.getCodigo());

        if(nego!=null){
            for(Cliente client:nego.getClientes()){
                if(client.getPerfil().getDNI().equals(negocio.getClientes().get(0).getPerfil().getDNI())){
                    return client;
                }
            }
        }


        return null;
    }

    public String checkWhatIsWrong(Negocio negocio){

        Negocio nego = NegocioService.getNegocioByCodigo(negocio.getCodigo());

        if(nego!=null){
            for(Cliente client:nego.getClientes()){
                if(client.getPerfil().getDNI().equals(negocio.getClientes().get(0).getPerfil().getDNI())){
                    return "Codigo de negocio is wrong.";
                }
            }
        }


        return "DNI is wrong.";

    }

}
