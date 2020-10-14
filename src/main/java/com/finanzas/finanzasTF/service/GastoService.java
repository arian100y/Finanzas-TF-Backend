package com.finanzas.finanzasTF.service;

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

    public List<Gasto> getAllGastos(){

        List<Gasto> gastos = new ArrayList<>();
        gastoRepository.findAll().forEach(gastos::add);

        return gastos;
    }
    public void addGasto(Gasto gasto){

        gastoRepository.save(gasto);
    }
}
