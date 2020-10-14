package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Perfil;
import com.finanzas.finanzasTF.models.Tasa;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import com.finanzas.finanzasTF.repository.TasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TasaService {
    @Autowired
    private TasaRepository tasaRepository;

    public List<Tasa> getAllTasas(){

        List<Tasa> tasas = new ArrayList<>();
        tasaRepository.findAll().forEach(tasas::add);

        return tasas;
    }
    public void addTasa(Tasa tasa){

        tasaRepository.save(tasa);
    }
}
