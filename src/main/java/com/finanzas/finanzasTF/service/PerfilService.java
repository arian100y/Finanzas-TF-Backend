package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Notificacion;
import com.finanzas.finanzasTF.models.Perfil;
import com.finanzas.finanzasTF.repository.NotificacionRepository;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<Perfil> getAllPerfiles(){

        List<Perfil> perfiles = new ArrayList<>();
        perfilRepository.findAll().forEach(perfiles::add);

        return perfiles;
    }
    public void addPerfil(Perfil perfil){

        perfilRepository.save(perfil);
    }
}
