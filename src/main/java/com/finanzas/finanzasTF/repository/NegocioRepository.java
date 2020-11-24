package com.finanzas.finanzasTF.repository;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio,Integer > {
    Negocio findOneNegocioByRUC(Long RUC);
    Negocio findOneNegocioByPerfil_id(Integer perfil_id);



    @Query(value = "SELECT EXISTS (SELECT * FROM negocios n WHERE  (n.ruc = :ruc) )",
            nativeQuery = true)
    Boolean existsRUC(
            @Param("ruc") Long ruc);

    @Query(value = "SELECT EXISTS (SELECT * FROM negocios n WHERE  (LOWER(n.codigo) = LOWER(:codigo)) )",
            nativeQuery = true)
    Boolean existsCodigo(
            @Param("codigo") String codigo);
}
