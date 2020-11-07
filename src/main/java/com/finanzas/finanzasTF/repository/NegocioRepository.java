package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio,Integer > {
    Negocio findOneByPerfilId(Integer perfil_id);
}
