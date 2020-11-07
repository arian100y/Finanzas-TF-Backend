package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAllByPerfilId(Integer perfil_id);
}
