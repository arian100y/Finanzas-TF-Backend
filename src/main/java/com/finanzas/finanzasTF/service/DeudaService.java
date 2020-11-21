package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.DeudaRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    public List<Deuda> getAllDeudas(){

        List<Deuda> deudas = new ArrayList<>();
        deudaRepository.findAll().forEach(deudas::add);

        return deudas;
    }
    public void addDeuda(Deuda deuda){
        LocalDateTime currentDate = LocalDateTime.now();
        deuda.setFecha(currentDate);
        deudaRepository.save(deuda);
    }
    public void putDeuda(Deuda deuda){
        deudaRepository.save(deuda);
    }

    public Deuda getDeudaFecha(Integer id){
        List<Deuda> deudas =new ArrayList<>();
        deudaRepository.findAll().forEach(deudas::add);
        LocalDateTime currentDate = LocalDateTime.now();
        Deuda clienteDeuda = new Deuda();
        for(Deuda deuda:deudas){
            if(deuda.getCliente_id().equals(id)){
                if(deuda.getFecha().getMonth().equals(currentDate.getMonth()) &&
                        deuda.getFecha().getYear() == currentDate.getYear()){
                    clienteDeuda =  deuda;
                }

            }
        }

        return clienteDeuda;
    }

    public void checkDeudas(){

        LocalDateTime currentDate = LocalDateTime.now();

        List<Deuda> allDeudas = new ArrayList<>(deudaRepository.findAll());
        for (Deuda ded : allDeudas) {
            if(ded.getFecha() != null){
                if(ded.getFecha().getMonthValue() +1 % 11 == currentDate.getMonthValue()){
                    if(ded.getFecha().getDayOfMonth() == currentDate.getDayOfMonth()){
                        Deuda newDeuda = new Deuda(null,ded.getCliente_id(),currentDate,0.0f,0.0f,true,
                                false);
                        deudaRepository.save(newDeuda);
                    }

                }
            }

        }
    }

    public float TNaTEP(Float tasa,Integer n,Integer m){

        return (float)(Math.pow(1+tasa/m ,n)-1);
    }
    public float TEPaTN(Float TEP,Integer n,Integer m){

        return (float)(m*(Math.pow(1+TEP,1/n)-1));
    }
    public float TEPaTEP(Float TEP1,Integer n2,Integer n1 ){

        return (float)(Math.pow((1+TEP1),(n2/n1)) - 1);
    }

    public float valorFuturoSimple(float C,float i, float t){
        return (float)(C*(1+i*t));
    }
    public float valorFuturoCompuesto(float C,float TN, int m , int n){

        return (float)(C*Math.pow((1+TN/m),n));
    }
    public float valorFuturoEfectivo(float C,float TEP,int nTras, int nTEP){

        return (float)(C*Math.pow(1+TEP,nTras/nTEP));
    }
    public float generateInterest(Deuda deuda){
        return valorFuturoEfectivo(deuda.getMonto(),1.0f,1,2)-deuda.getMonto();
    }
    public void updateDeuda(Deuda deuda){
    deuda.setInteres(generateInterest(deuda));

    deudaRepository.save(deuda);

    }
}
