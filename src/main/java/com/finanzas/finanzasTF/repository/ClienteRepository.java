package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query(
            value = "SELECT * FROM Clientes c INNER JOIN Perfils p ON c.perfil_id = p.id WHERE p.DNI = :DNI",
            nativeQuery = true)
    Cliente findClienteByDNI(@Param("DNI") Integer DNI);
}
