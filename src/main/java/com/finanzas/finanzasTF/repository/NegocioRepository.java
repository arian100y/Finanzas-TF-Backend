package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio,Integer > {
}
