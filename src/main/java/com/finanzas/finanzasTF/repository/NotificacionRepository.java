package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion,Integer> {
}
