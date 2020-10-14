package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda,Integer> {
}
