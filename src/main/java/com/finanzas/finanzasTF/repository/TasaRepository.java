package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Tasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasaRepository extends JpaRepository<Tasa,Integer> {
}
