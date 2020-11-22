package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.models.Perfil;
import com.finanzas.finanzasTF.repository.NegocioRepository;
import com.finanzas.finanzasTF.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NegocioService {

    @Autowired
    private NegocioRepository negocioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    public List<Negocio> getAllNegocios(){

        List<Negocio> negocios = new ArrayList<>();
        negocioRepository.findAll().forEach(negocios::add);

        return negocios;
    }

    public Negocio getNegocioById(Integer id){
        return negocioRepository.findById(id).get();
    }

    public ResponseEntity<? > addNegocio(Negocio negocio){

        if(this.negocioRepository.existsRUC(negocio.getRUC())){
            return new ResponseEntity<String>("El RUC ya esta registrado.", HttpStatus.FORBIDDEN);
        }
        if(this.negocioRepository.existsCodigo(negocio.getCodigo())){
            return new ResponseEntity<String>("El codigo de tienda ya esta registrado.", HttpStatus.FORBIDDEN);
        }
        Perfil temp = new Perfil();
        temp = negocio.getPerfil();
        perfilRepository.save(temp);


        negocioRepository.save(negocio);
        return new ResponseEntity<String>( HttpStatus.CREATED);

    }
    public void deleteAll(){
        negocioRepository.deleteAll();
    }
    public Negocio verifyLogin(Negocio negocio){
        List<Negocio> allNegocios = negocioRepository.findAll();

        for(Negocio nego:allNegocios){
            if(nego.getRUC() != null && nego.getPassword() != null){
                if(nego.getRUC().equals(negocio.getRUC()) && nego.getPassword().equals(negocio.getPassword())){
                    return nego;
                }
            }

        }

        return null;
    }
    public Negocio getNegocioByCodigo(String codigo){
        List<Negocio> allNegocios = negocioRepository.findAll();


        for(Negocio nego:allNegocios)
            if(nego.getCodigo() != null){

                if(nego.getCodigo().equals(codigo)){

                    return nego;
                }
        }

        return null;
    }
    public Boolean check(Negocio negocio){
        List<Negocio> allNegocios = negocioRepository.findAll();

        for(Negocio nego:allNegocios){
            if(nego.getRUC() != null && nego.getPassword() != null){
                if(nego.getRUC().equals(negocio.getRUC()) && nego.getPassword().equals(negocio.getPassword())){
                    return true;
                }
            }

        }

        return false;
    }
    public String checkWhatIsWrong(Negocio negocio){
        List<Negocio> allNegocios = negocioRepository.findAll();
        for(Negocio nego:allNegocios){
            if(nego.getRUC() != null){
                if(nego.getRUC().equals(negocio.getRUC())){
                    return "El password es erróneo. Vuelva a intentarlo.";
                }
            }

        }
        return "El RUC es erróneo. Vuelva a intentarlo.";
    }

    public Negocio getNegocioByRUC(Integer RUC){
        return negocioRepository.findOneNegocioByRUC(RUC);
    }

    public Negocio getNegocioByPerfil_id(Integer id) {
        return negocioRepository.findById(id).get();
    }

}
