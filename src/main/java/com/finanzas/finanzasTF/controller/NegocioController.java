package com.finanzas.finanzasTF.controller;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Negocio;
import com.finanzas.finanzasTF.service.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/negocios")
public class NegocioController {


    @Autowired
    private final NegocioService negocioService;

    public NegocioController(NegocioService negocioService){
        this.negocioService = negocioService;
    }

    @GetMapping
    public List<Negocio> getAllNegocios(){
        return negocioService.getAllNegocios();
    }

    @PostMapping()
    public ResponseEntity<? >  addNegocio(@RequestBody Negocio negocio){
        System.out.println("testing");
        
        return negocioService.addNegocio(negocio);
    }

    @PostMapping("/delete")
    public void delete(){
        negocioService.deleteAll();
    }

    @PostMapping("/login")
    public ResponseEntity<? > checkLogin(@RequestBody Negocio negocio) {

        if (negocioService.check(negocio) == true) {

            Negocio temp = negocioService.verifyLogin(negocio);
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } else {

            String response = negocioService.checkWhatIsWrong(negocio);
            return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/RUC={RUC}")
    public Negocio getNegocioByRUC(@PathVariable("RUC") Integer RUC){
        return negocioService.getNegocioByRUC(RUC);
    }

    @GetMapping("/perfil_id={perfil_id}")
    public Negocio getNegocioByPerfil_id(@PathVariable("perfil_id") Integer perfil_id){
        return negocioService.getNegocioByPerfil_id(perfil_id);
    }
}
