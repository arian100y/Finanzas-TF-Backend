package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil,Integer > {

}
