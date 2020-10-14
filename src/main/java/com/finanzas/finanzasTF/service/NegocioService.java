package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.models.Perfil;
import com.finanzas.finanzasTF.repository.NegocioRepository;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NegocioService {

    @Autowired
    private NegocioRepository negocioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    public List<Negocio> getAllNegocios(){

        List<Negocio> negocios = new ArrayList<>();
        negocioRepository.findAll().forEach(negocios::add);

        return negocios;
    }
    public void addNegocio(Negocio negocio){
        Perfil temp = new Perfil();
        temp = negocio.getPerfil();
        perfilRepository.save(temp);

        negocioRepository.save(negocio);
    }
}
