package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.DeudaRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    public List<Deuda> getAllDeudas(){

        List<Deuda> deudas = new ArrayList<>();
        deudaRepository.findAll().forEach(deudas::add);

        return deudas;
    }
    public void addDeuda(Deuda deuda){

        deudaRepository.save(deuda);
    }

    public Deuda getDeudaFecha(Integer id){
        List<Deuda> deudas =new ArrayList<>();
        deudaRepository.findAll().forEach(deudas::add);
        LocalDateTime currentDate = LocalDateTime.now();
        Deuda clienteDeuda = new Deuda();
        for(Deuda deuda:deudas){
            if(deuda.getCliente_id().equals(id)){
                if(deuda.getFecha().getMonth().equals(currentDate.getMonth()) &&
                        deuda.getFecha().getYear() == currentDate.getYear()){
                    clienteDeuda =  deuda;
                }

            }
        }

        return clienteDeuda;
    }
}
