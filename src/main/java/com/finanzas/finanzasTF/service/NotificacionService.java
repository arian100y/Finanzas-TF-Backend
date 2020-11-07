package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Gasto;
import com.finanzas.finanzasTF.models.Notificacion;
import com.finanzas.finanzasTF.repository.GastoRepository;
import com.finanzas.finanzasTF.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> getAllNotificaciones(){

        List<Notificacion> notificaciones = new ArrayList<>();
        notificacionRepository.findAll().forEach(notificaciones::add);

        return notificaciones;
    }
    public void addNotificacion(Notificacion notif){

        notificacionRepository.save(notif);
    }
}
