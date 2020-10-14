package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Notificacion;
import com.finanzas.finanzasTF.models.Tasa;
import com.finanzas.finanzasTF.service.NotificacionService;
import com.finanzas.finanzasTF.service.TasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private final NotificacionService notificacionServices;
    public NotificacionController(NotificacionService notificacionServices){
        this.notificacionServices = notificacionServices;
    }

    @GetMapping
    public List<Notificacion> getAllNotificaciones(){
        return notificacionServices.getAllNotificaciones();
    }

    @PostMapping
    public void addNotificacion(@RequestBody Notificacion notif){
        notificacionServices.addNotificacion(notif);
    }

}
