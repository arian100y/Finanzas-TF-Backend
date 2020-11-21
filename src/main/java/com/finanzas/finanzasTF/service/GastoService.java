package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.models.Gasto;
import com.finanzas.finanzasTF.repository.DeudaRepository;
import com.finanzas.finanzasTF.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private DeudaRepository deudaRepository;
    @Autowired
    private DeudaService deudaService;
    public List<Gasto> getAllGastos(){

        List<Gasto> gastos = new ArrayList<>();
        gastoRepository.findAll().forEach(gastos::add);

        return gastos;
    }
    public void addGasto(Gasto gasto){

        gastoRepository.save(gasto);
        Deuda deuda = deudaRepository.findById(gasto.getDeuda_id()).get();
        deuda.setMonto(deuda.getMonto() + gasto.getMonto());
        deudaService.updateDeuda(deuda);
        Cliente cliente = new Cliente();
        float newCredito = Float.parseFloat(cliente.getCredito())-gasto.getMonto();
        cliente.setCredito(String.valueOf(newCredito));
        //deudaRepository.save(deuda);
    }
    public List<Gasto> getAllByUserId(Integer id){
        return gastoRepository.findGastoByUserId(id);
    }
    public void deleteAll(){
        gastoRepository.deleteAll();
    }
}
