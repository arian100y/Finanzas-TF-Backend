package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import com.finanzas.finanzasTF.repository.TasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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

    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

    public void editCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public ResponseEntity<?> verify(Cliente cliente) {
        //perfilRepository.save(cliente.getPerfil());
        //System.out.println(cliente.getId());

        Negocio nego = NegocioService.getNegocioById(cliente.getNegocio_id());
        for (Cliente client :
                nego.getClientes()) {
            if (client.getPerfil().getDNI() != null) {
                System.out.println("HEE");
                if (client.getPerfil().getDNI().equals(cliente.getPerfil().getDNI())) {
                    return new ResponseEntity<String>("El DNI ya esta registrado con este negocio.", HttpStatus.FORBIDDEN);
                }
            }

        }


        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public void addCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente verifyLogin(Negocio negocio) {

        Negocio nego = NegocioService.getNegocioByCodigo(negocio.getCodigo());

        if (nego != null) {
            for (Cliente client : nego.getClientes()) {

                if (client.getPerfil().getDNI() != null) {

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

        if (c == null) {
            return "El DNI no existe. Vuelva a intentarlo.";
        }
        if (nego == null) {
            return "El negocio no existe. Vuelva a intentarlo.";
        }


        return "El DNI no pertenece a la tienda. Vuelva a intentarlo.";

    }

    public void checkMantenimiento(LocalDateTime date) {
        Integer[] days = {7, 15, 30};
        List<Cliente> clientes = new ArrayList<>(clienteRepository.findAll());
        LocalDateTime currentDate;
        currentDate = Objects.requireNonNullElseGet(date, LocalDateTime::now);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        Integer daysCurrent = Integer.parseInt(currentDate.format(formatter));

        for (Cliente cliente : clientes) {
            Integer daysMantenimiento = days[cliente.getPeriodoMantenimiento()];
            if (daysCurrent % daysMantenimiento == daysMantenimiento && getLastDeuda(cliente).getMonto() == 0) {
                cliente.setMontoMantenimiento(Float.parseFloat(cliente.getCredito()) * cliente.getMantenimiento());
            }
        }
    }

    public Deuda getLastDeuda(Cliente cliente) {

        List<Deuda> deudas = cliente.getDeudas();

        deudas.sort((s1, s2) -> s2.getId().compareTo(s1.getId()));

        return deudas.get(0);
    }

    public Cliente getClienteByDNI(Integer DNI) {
        return clienteRepository.findClienteByDNI(DNI);
    }

    public Cliente getClienteByPerfil_id(Integer perfil_id) {
        return clienteRepository.findOneClienteByPerfil_id(perfil_id);
    }
}
