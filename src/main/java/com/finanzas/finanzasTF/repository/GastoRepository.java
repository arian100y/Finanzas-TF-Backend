package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto,Integer> {
    @Query(
            value = "SELECT * FROM gastos g WHERE g.deuda_id = :id",
            nativeQuery = true)
    List<Gasto> findGastoByUserId(@Param("id") Integer id);
}
