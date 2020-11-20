package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import com.finanzas.finanzasTF.repository.TasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private NegocioService NegocioService;
    @Autowired
    private TasaRepository tasaRepository;


    public List<Cliente> getAllClientes() {

        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);

        return clientes;
    }

    public void delete() {
        clienteRepository.deleteAll();
    }

    public void deleteById(Integer id){
        clienteRepository.deleteById(id);
    }

    public ResponseEntity<?> verify(Cliente cliente) {
        //perfilRepository.save(cliente.getPerfil());
        //System.out.println(cliente.getId());

        Negocio nego = NegocioService.getNegocioById(cliente.getNegocio_id());
        for (Cliente client:
             nego.getClientes()) {
            if(client.getPerfil().getDNI() != null){
                System.out.println("HEE");
                if(client.getPerfil().getDNI().equals(cliente.getPerfil().getDNI())){
                    return new ResponseEntity<String>("El DNI ya esta registrado con este negocio.", HttpStatus.FORBIDDEN);
                }
            }

        }


        return new ResponseEntity<String>( HttpStatus.OK);
    }

    public void addCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }
    public Cliente verifyLogin(Negocio negocio) {

        Negocio nego = NegocioService.getNegocioByCodigo(negocio.getCodigo());

        if (nego != null) {
            for (Cliente client : nego.getClientes()) {

                if(client.getPerfil().getDNI() != null){

                    if (client.getPerfil().getDNI().equals(negocio.getClientes().get(0).getPerfil().getDNI())) {
                        return client;
                    }
                }

            }
        }


        return null;
    }

    public String checkWhatIsWrong(Negocio negocio) {

        Negocio nego = NegocioService.getNegocioByCodigo(negocio.getCodigo());

        Cliente c = clienteRepository.findClienteByDNI(negocio.getClientes().get(0).getPerfil().getDNI());

        if(c == null){
            return "El DNI no existe. Vuelva a intentarlo.";
        }
        if(nego == null){
            return "El negocio no existe. Vuelva a intentarlo.";
        }



       return "El DNI no pertenece a la tienda. Vuelva a intentarlo.";

    }

    public Cliente getClienteByDNI(Integer DNI){
        return clienteRepository.findClienteByDNI(DNI);
    }
    public Cliente getClienteByPerfil_id(Integer perfil_id) {return clienteRepository.findOneClienteByPerfil_id(perfil_id);}
}
