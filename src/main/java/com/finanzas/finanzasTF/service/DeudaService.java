package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.models.Tasa;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.DeudaRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void cobrarDeuda(){

    }
   public Deuda getLastDeuda(Cliente cliente){

       List<Deuda> deudas = cliente.getDeudas();

       deudas.sort((s1, s2) -> s2.getId().compareTo(s1.getId()));

     return deudas.get(0);
   }
    @Autowired
    ClienteRepository clienteRepository;
    public void checkDeudas(){


        LocalDateTime currentDate = LocalDateTime.now();




        List<Deuda> allDeudas = new ArrayList<>(deudaRepository.findAll());
        for (Deuda ded : allDeudas) {
            if(ded.getFecha() != null){
                if(ded.getFecha().getMonthValue() +1 % 11 == currentDate.getMonthValue()){
                    if(ded.getFecha().getDayOfMonth() == currentDate.getDayOfMonth()){
                        Deuda newDeuda = new Deuda(null,ded.getCliente_id(),currentDate,0.0f,0.0f,true, false);
                        deudaRepository.save(newDeuda);

                    }

                }
            }

        }

    }

    public void checkInteres(){
        LocalDateTime currentDate = LocalDateTime.now();
        List<Cliente> clientes = clienteRepository.findAll();
        this.getLastDeuda(clientes.get(0)).getId();

        for(Cliente cliente:clientes){
            System.out.println("WTF");
            Deuda lastDeuda = this.getLastDeuda(cliente);
            Integer monthVal = lastDeuda.getFecha().getMonthValue()  % 13 ;
            if(monthVal == 0){monthVal += 1;}
            System.out.println(monthVal);
            System.out.println(currentDate.getMonthValue());
            if(monthVal.equals(currentDate.getMonthValue()) ) {

                Integer monthDate = lastDeuda.getFecha().getDayOfMonth();
                if (monthDate.equals(currentDate.getDayOfMonth())  ) {

                    //this.updateDeuda(lastDeuda);
                    //generate interest?!??
                    Deuda newDeuda = new Deuda(null,lastDeuda.getCliente_id(),
                            currentDate,0.0f,0.0f,true,
                            false);
                    deudaRepository.save(newDeuda);

                }

            }


        }
    }
    public void generateInterest(){

        List<Cliente> clientes = clienteRepository.findAll();
        this.getLastDeuda(clientes.get(0)).getId();

        for(Cliente cliente:clientes){

            Deuda lastDeuda = this.getLastDeuda(cliente);
            Integer monthVal = lastDeuda.getFecha().getMonthValue()  % 13 ;

            if(monthVal == 0){monthVal += 1;}
            LocalDateTime newDate = lastDeuda.getFecha();
            newDate = newDate.plusMonths(1);
            System.out.println(newDate);




            this.updateDeudaInteres(lastDeuda,cliente.getTasa());

           Deuda newDeuda = new Deuda(null,cliente.getId(),
                        newDate,0.0f,0.0f,true,
                          false);

           deudaRepository.save(newDeuda);

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
    public float valorFuturoCompuesto(float C,float TN, float m , float n){
        if(TN > 1){
            TN = TN/100;
        }

        return (float)(C*Math.pow((1+TN/m),n));
    }
    public float valorFuturoEfectivo(float C,float TEP,int nTras, int nTEP){

        if(TEP > 1){
            TEP = TEP/100;
        }
        return (float)(C*Math.pow(1+TEP,nTras/nTEP));
    }
    public float getN(Tasa tasa){
        float n ;
        if(tasa.getPeriodoCapitalizacion() == 0){
            n= 30;
        }else if(tasa.getPeriodoCapitalizacion()==1){
            n= 4;
        }else if(tasa.getPeriodoCapitalizacion()==2){
            n= 2;
        }else if(tasa.getPeriodoCapitalizacion()==3){
            n= 1;
        }
        else if(tasa.getPeriodoCapitalizacion()==4){
            n= 1/2;
        }
        else if(tasa.getPeriodoCapitalizacion()==5){
           n= 1/3;
        }
        else if(tasa.getPeriodoCapitalizacion()==6){
            n= 1/4;
        }
        else if(tasa.getPeriodoCapitalizacion()==7){
            n= 1/6;
        }else {
            n= 1/12;
        }

        return n;
    }
    public float getM(float n , Tasa tasa){
        float m;

        if(tasa.getPeriodo() == 0){
            m= n * 1/30;
        }else if(tasa.getPeriodo()==1){
            m = n * 1/4;
        }else if(tasa.getPeriodo()==2){
            m = n * 1/2;
        }else if(tasa.getPeriodo()==3){
            m = n * 1;
        }
        else if(tasa.getPeriodo()==4){
            m = n * 2;
        }
        else if(tasa.getPeriodo()==5){
            m = n * 3;
        }
        else if(tasa.getPeriodo()==6){
            m = n * 4;
        }
        else if(tasa.getPeriodo()==7){
            m = n * 6;
        }else {
            m = n * 12;
        }

        return m;
    }
    public float generateInterest(Deuda deuda,Tasa tasa){
        System.out.println(tasa.getTipo());

        //Simple
        if(tasa.getTipo()== 0){
            float t ;
            if(tasa.getPeriodo() == 0){
                t= 30;
            }else if(tasa.getPeriodo()==1){
                t= 4;
            }else if(tasa.getPeriodo()==2){
                t= 2;
            }else if(tasa.getPeriodo()==3){
                t= 1;
            }
            else if(tasa.getPeriodo()==4){
                t= 1/2;
            }
            else if(tasa.getPeriodo()==5){
                t= 1/3;
            }
            else if(tasa.getPeriodo()==6){
                t= 1/4;
            }
            else if(tasa.getPeriodo()==7){
                t= 1/6;
            }else {
                t= 1/12;
            }

            return valorFuturoSimple(deuda.getMonto(),tasa.getMonto(),t)-deuda.getMonto();
        }//Nominal
        else if(tasa.getTipo()==1){

            float m , n;
            n  =this.getN(tasa);
            m  =this.getM(n,tasa);

            System.out.println("MONTO DEUDA: "+deuda.getMonto());
            System.out.println("MONTO TASA: "+tasa.getMonto());
            System.out.println("Periodo capita TASA: "+tasa.getPeriodoCapitalizacion());
            System.out.println("type tasa:" + tasa.getPeriodo());
            System.out.println("N: " + n);
            System.out.println("M: "+m);
            return valorFuturoCompuesto(deuda.getMonto(),tasa.getMonto(),m,n)-deuda.getMonto();
        }//Efectiva
        else{
            int days;

            if(tasa.getPeriodo() == 0){
                days =1;
            }else if(tasa.getPeriodo()==1){
                days =7;
            }else if(tasa.getPeriodo()==2){
                days =15;
            }else if(tasa.getPeriodo()==3){
                days =30;
            }
            else if(tasa.getPeriodo()==4){
                days =60;
            }
            else if(tasa.getPeriodo()==5){
                days =90;
            }
            else if(tasa.getPeriodo()==6){
                days =120;
            }
            else if(tasa.getPeriodo()==7){
                days =180;
            }else {
                days =360;
            }


            return valorFuturoEfectivo(deuda.getMonto(),tasa.getMonto(),30,days)-deuda.getMonto();
        }


    }

    public void updateDeudaInteres(Deuda deuda,Tasa tasa){
        deuda.setInteres(generateInterest(deuda,tasa));
        deudaRepository.save(deuda);
    }
    public void updateDeuda(Deuda deuda){


    deudaRepository.save(deuda);

    }
}
