package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto,Integer> {
}
