package com.finanzas.finanzasTF.service;

import com.finanzas.finanzasTF.models.Cliente;
import com.finanzas.finanzasTF.models.Deuda;
import com.finanzas.finanzasTF.models.Tasa;
import com.finanzas.finanzasTF.repository.ClienteRepository;
import com.finanzas.finanzasTF.repository.DeudaRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import static java.time.temporal.ChronoUnit.DAYS;

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

            Deuda lastDeuda = this.getLastDeuda(cliente);
            Integer monthVal = lastDeuda.getFecha().getMonthValue()  % 13 ;
            if(monthVal == 0){monthVal += 1;}

            if(monthVal.equals(currentDate.getMonthValue()) ) {

                Integer monthDate = lastDeuda.getFecha().getDayOfMonth();
                if (monthDate.equals(currentDate.getDayOfMonth())  ) {

                    this.updateDeudaInteres(lastDeuda,cliente.getTasa(),LocalDateTime.now());

                    Deuda newDeuda = new Deuda(null,lastDeuda.getCliente_id(),
                            currentDate,0.0f,0.0f,true,
                            false);
                    deudaRepository.save(newDeuda);

                }

            }


        }
    }
    public void generateInterest(LocalDateTime simDate){


        List<Cliente> clientes = clienteRepository.findAll();
        this.getLastDeuda(clientes.get(0)).getId();

        for(Cliente cliente:clientes){

            Deuda lastDeuda = this.getLastDeuda(cliente);


            this.updateDeudaInteres(lastDeuda,cliente.getTasa(),simDate);

            LocalDateTime newDate = lastDeuda.getFecha();
            newDate = newDate.plusMonths(1);
           //Deuda newDeuda = new Deuda(null,cliente.getId(),
             //           newDate,0.0f,0.0f,true,
               //           false);

           //deudaRepository.save(newDeuda);

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


        return (float)(C*Math.pow((1+TN/m),n));
    }
    public float valorFuturoEfectivo(float C,float TEP,long nTras, long nTEP){

        if(TEP > 1){
            TEP = TEP/100;
        }
        return (float)(C*Math.pow(1+TEP,(float)nTras/nTEP));
    }
    public float getN(Tasa tasa,Deuda deuda,LocalDateTime date){
        LocalDate dateBefore = deuda.getFecha().toLocalDate().minusMonths(1);
        LocalDate dateAfter = date.toLocalDate();
//        System.out.println(dateBefore);
//        System.out.println(dateAfter);
        long daysBetween = DAYS.between(dateBefore, dateAfter);
        long daysBetweenFecha = DAYS.between(dateBefore, deuda.getFecha().toLocalDate());

        float n ;
        //diaria
        if(tasa.getPeriodo() == 0){
            n= (float)(daysBetween);
        }else if(tasa.getPeriodo()==1){
            n= 4;
        }else if(tasa.getPeriodo()==2){
            n= (float)(daysBetween)/15;
        }else if(tasa.getPeriodo()==3){
            n= ((float)(daysBetween)/daysBetweenFecha);

            System.out.println("dbet "+n);
        }
        else if(tasa.getPeriodo()==4){
            n= (float)(daysBetween)/DAYS.between(dateBefore, dateBefore.plusMonths(2));

        }
        else if(tasa.getPeriodo()==5){
            n= (float)(daysBetween)/DAYS.between(dateBefore, dateBefore.plusMonths(3));
        }
        else if(tasa.getPeriodo()==6){
            n= (float)(daysBetween)/DAYS.between(dateBefore, dateBefore.plusMonths(4));
        }
        else if(tasa.getPeriodo()==7){
            n= (float)(daysBetween)/DAYS.between(dateBefore, dateBefore.plusMonths(6));
        }else {
            n= (float)(daysBetween)/DAYS.between(dateBefore, dateBefore.plusMonths(12));
        }



        return n;
    }
    public float getM(float n , Tasa tasa,Deuda deuda,LocalDateTime date){
        float m;
        LocalDate dateBefore = deuda.getFecha().toLocalDate().minusMonths(1);
        LocalDate dateAfter = date.toLocalDate();
//        System.out.println(dateBefore);
//        System.out.println(dateAfter);
        long daysBetween = DAYS.between(dateBefore, dateAfter);


        //diario
        if(tasa.getPeriodo() == 0){
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = 1;
            }if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 15;
            }if(tasa.getPeriodoCapitalizacion() == 3){
                m =  DAYS.between(dateBefore, dateBefore.plusMonths(1));
            }if(tasa.getPeriodoCapitalizacion() == 4 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(2));
            }if(tasa.getPeriodoCapitalizacion() == 5){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(3));
            }if(tasa.getPeriodoCapitalizacion() == 6 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(4));
            }if(tasa.getPeriodoCapitalizacion() == 7){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(6));
            }else{
                m = DAYS.between(dateBefore, dateBefore.plusMonths(12));
            }



        }
        //semanal
        else if(tasa.getPeriodo()==1){
            m = n * 1/4;
        }
        //quincenal
        else if(tasa.getPeriodo()==2){
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = 15;
            }if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 1;
            }if(tasa.getPeriodoCapitalizacion() == 3){
                //m =    15/DAYS.between(dateBefore, dateBefore.plusMonths(1));
                m = 1/2 ;
            }if(tasa.getPeriodoCapitalizacion() == 4 ){
                //m = 15/DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=1/4;
            }if(tasa.getPeriodoCapitalizacion() == 5){
                //m = 15/DAYS.between(dateBefore, dateBefore.plusMonths(3));
                m= 1/6;
            }if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m = 15/DAYS.between(dateBefore, dateBefore.plusMonths(4));
                m=1/8;
            }if(tasa.getPeriodoCapitalizacion() == 7){
                //m = 15/DAYS.between(dateBefore, dateBefore.plusMonths(6));
                m=1/12;
            }else{
                //m = 15/DAYS.between(dateBefore, dateBefore.plusMonths(12));
                m=1/24;
            }

        }
        //mensual
        else if(tasa.getPeriodo()==3){

            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(1));
            }else if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }else if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 2;
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(1))/15;
            }else if(tasa.getPeriodoCapitalizacion() == 3){

                m = 1 ;
            }else if(tasa.getPeriodoCapitalizacion() == 4 ){
                //m =DAYS.between(dateBefore, dateBefore.plusMonths(1))/ DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=1/2;
            }else if(tasa.getPeriodoCapitalizacion() == 5){
                //m =DAYS.between(dateBefore, dateBefore.plusMonths(1)) /DAYS.between(dateBefore, dateBefore.plusMonths(3));
                m= 1/3;
            }else if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m =DAYS.between(dateBefore, dateBefore.plusMonths(1))/ DAYS.between(dateBefore, dateBefore.plusMonths(4));
                m=1/4;
            }else if(tasa.getPeriodoCapitalizacion() == 7){
                //m =DAYS.between(dateBefore, dateBefore.plusMonths(1))/ DAYS.between(dateBefore, dateBefore.plusMonths(6));
                m=1/6;
            }else{
                //m =DAYS.between(dateBefore, dateBefore.plusMonths(1))/ DAYS.between(dateBefore, dateBefore.plusMonths(12));
                m=1/12;
            }
        }
        //bimestral
        else if(tasa.getPeriodo()==4){
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(2));
            }if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 4;
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(1))/15;
            }if(tasa.getPeriodoCapitalizacion() == 3){
                // m = DAYS.between(dateBefore, dateBefore.plusMonths(2))/DAYS.between(dateBefore, dateBefore.plusMonths(1));
                m = 2 ;
            }if(tasa.getPeriodoCapitalizacion() == 4 ){
                // m = DAYS.between(dateBefore, dateBefore.plusMonths(2))/DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=1;
            }if(tasa.getPeriodoCapitalizacion() == 5){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(2))/DAYS.between(dateBefore, dateBefore.plusMonths(3));
                m= 2/3;
            }if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(2))/DAYS.between(dateBefore, dateBefore.plusMonths(4));
                m=1/2;
            }if(tasa.getPeriodoCapitalizacion() == 7){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(2))/DAYS.between(dateBefore, dateBefore.plusMonths(6));
                m=2/6;
            }else{
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(2))/DAYS.between(dateBefore, dateBefore.plusMonths(12));
                m=2/12;
            }
        }
        //trimestral
        else if(tasa.getPeriodo()==5){
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(3));
            }if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }//quincenal
            if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 6;
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/15;
            }if(tasa.getPeriodoCapitalizacion() == 3){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/DAYS.between(dateBefore, dateBefore.plusMonths(1));
                m = 3 ;
            }if(tasa.getPeriodoCapitalizacion() == 4 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=3/2;
            }if(tasa.getPeriodoCapitalizacion() == 5){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/DAYS.between(dateBefore, dateBefore.plusMonths(3));
                m= 3/3;
            }if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/DAYS.between(dateBefore, dateBefore.plusMonths(4));
                m=3/4;
            }if(tasa.getPeriodoCapitalizacion() == 7){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/DAYS.between(dateBefore, dateBefore.plusMonths(6));
                m=3/6;
            }else{
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(3))/DAYS.between(dateBefore, dateBefore.plusMonths(12));
                m=3/12;
            }
        }
        //cuatrimestral
        else if(tasa.getPeriodo()==6){
            //diario
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(4));
            }//NOT
            if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }//quincenal
            if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 8;
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/15;
            }//mensual
            if(tasa.getPeriodoCapitalizacion() == 3){
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/DAYS.between(dateBefore, dateBefore.plusMonths(1));
                m = 4 ;
            }//bimestral
            if(tasa.getPeriodoCapitalizacion() == 4 ){
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=4/2;
            }//trimetral
            if(tasa.getPeriodoCapitalizacion() == 5){
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/DAYS.between(dateBefore, dateBefore.plusMonths(3 ));
                m= 4/3;
            }//cuatrimestral
            if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/DAYS.between(dateBefore, dateBefore.plusMonths(4 ));
                m=4/4;
            }//semestral
             if(tasa.getPeriodoCapitalizacion() == 7){
                 //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/DAYS.between(dateBefore, dateBefore.plusMonths(6 ));
                m=4/6;
            }else{
                 //m =   DAYS.between(dateBefore, dateBefore.plusMonths(4))/DAYS.between(dateBefore, dateBefore.plusMonths(12 ));
                m=4/12;
            }
        }
        //semestral
        else if(tasa.getPeriodo()==7){
            //diario
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(6));
            }//NOT
            if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }//quincenal
            if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 12;
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(6))/15;
            }//mensual
            if(tasa.getPeriodoCapitalizacion() == 3){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(6))/DAYS.between(dateBefore, dateBefore.plusMonths(1));
                m = 6 ;
            }//bimestral
            if(tasa.getPeriodoCapitalizacion() == 4 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(6))/DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=6/2;
            }//trimetral
            if(tasa.getPeriodoCapitalizacion() == 5){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(6))/DAYS.between(dateBefore, dateBefore.plusMonths(3));
                m= 6/3;
            }//cuatrimestral
            if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(6))/DAYS.between(dateBefore, dateBefore.plusMonths(4));
                m=6/4;
            }//semestral
            if(tasa.getPeriodoCapitalizacion() == 7){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(6))/DAYS.between(dateBefore, dateBefore.plusMonths(6));
                m=6/6;
            }else{
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(6))/DAYS.between(dateBefore, dateBefore.plusMonths(12));
                m=6/12;
            }
        }
        //anual
        else {
            //diario
            if(tasa.getPeriodoCapitalizacion() == 0 ){
                m = DAYS.between(dateBefore, dateBefore.plusMonths(12));
            }//NOT
            if(tasa.getPeriodoCapitalizacion() == 1 ){
                m = 00000;
            }//quincenal
            if(tasa.getPeriodoCapitalizacion() == 2 ){
                m = 24;
                //m =   DAYS.between(dateBefore, dateBefore.plusMonths(12))/15;
            }//mensual
            if(tasa.getPeriodoCapitalizacion() == 3){
//m = DAYS.between(dateBefore, dateBefore.plusMonths(12))/DAYS.between(dateBefore, dateBefore.plusMonths(1));
                m = 12 ;
            }//bimestral
            if(tasa.getPeriodoCapitalizacion() == 4 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(12))/DAYS.between(dateBefore, dateBefore.plusMonths(2));
                m=12/2;
            }//trimetral
            if(tasa.getPeriodoCapitalizacion() == 5){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(12))/DAYS.between(dateBefore, dateBefore.plusMonths(3));
                m= 12/3;
            }//cuatrimestral
            if(tasa.getPeriodoCapitalizacion() == 6 ){
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(12))/DAYS.between(dateBefore, dateBefore.plusMonths(4));
                m=12/4;
            }//semestral
            if(tasa.getPeriodoCapitalizacion() == 7){
                ///m = DAYS.between(dateBefore, dateBefore.plusMonths(12))/DAYS.between(dateBefore, dateBefore.plusMonths(6));
                m=12/6;
            }else{
                //m = DAYS.between(dateBefore, dateBefore.plusMonths(12))/DAYS.between(dateBefore, dateBefore.plusMonths(12));
                m=12/12;
            }
        }

        return m;
    }
    public float genInterest(Deuda deuda,Tasa tasa,LocalDateTime date){

        LocalDate dateBefore = deuda.getFecha().toLocalDate().minusMonths(1);
        LocalDate dateAfter = date.toLocalDate();
//       System.out.println(dateBefore);
//        System.out.println(dateAfter);
        long daysBetween = DAYS.between(dateBefore, dateAfter);
        long daysBetweenFecha = DAYS.between(dateBefore, deuda.getFecha().toLocalDate());
        System.out.println ("Days: " + daysBetween);

        //Simple
        if(tasa.getTipo()== 0){
            float t ;
            if(tasa.getPeriodo() == 0){
                t= daysBetween;
            }else if(tasa.getPeriodo()==1){
                t= 4;
            }else if(tasa.getPeriodo()==2){
                t= daysBetween/15;
            }else if(tasa.getPeriodo()==3){
                t= daysBetween/daysBetweenFecha;
            }
            else if(tasa.getPeriodo()==4){
                t= daysBetween/DAYS.between(dateBefore, dateBefore.plusMonths(2));
            }
            else if(tasa.getPeriodo()==5){
                t= daysBetween/DAYS.between(dateBefore, dateBefore.plusMonths(3));
            }
            else if(tasa.getPeriodo()==6){
                t= daysBetween/DAYS.between(dateBefore, dateBefore.plusMonths(4));
            }
            else if(tasa.getPeriodo()==7){
                t= daysBetween/DAYS.between(dateBefore, dateBefore.plusMonths(6));
            }else {
                t= daysBetween/DAYS.between(dateBefore, dateBefore.plusMonths(12));
            }

            return valorFuturoSimple(deuda.getMonto(),tasa.getMonto(),t)-deuda.getMonto();
        }
        //Nominal
        else if(tasa.getTipo()==1){

            float m , n;
            n  =this.getN(tasa,deuda,date);
            m  =this.getM(n,tasa,deuda,date);

            System.out.println("MONTO DEUDA: "+deuda.getMonto());
            System.out.println("MONTO TASA: "+tasa.getMonto());
            System.out.println("Periodo capita TASA: "+tasa.getPeriodoCapitalizacion());
            System.out.println("type tasa:" + tasa.getPeriodo());
            System.out.println("N: " + n);
            System.out.println("M: "+m);

            return valorFuturoCompuesto(deuda.getMonto(),tasa.getMonto(),m,n)-deuda.getMonto();
        }
        //Efectiva
        else{
            long days;

            if(tasa.getPeriodo() == 0){
                days =1;
            }else if(tasa.getPeriodo()==1){
                days =000000000;
            }else if(tasa.getPeriodo()==2){
                days =15;
            }else if(tasa.getPeriodo()==3){
                days = DAYS.between(dateBefore,dateBefore.plusMonths(1));

            }
            else if(tasa.getPeriodo()==4){
                days = DAYS.between(dateBefore,dateBefore.plusMonths(2));
            }
            else if(tasa.getPeriodo()==5){
                days = DAYS.between(dateBefore,dateBefore.plusMonths(3));
            }
            else if(tasa.getPeriodo()==6){
                days = DAYS.between(dateBefore,dateBefore.plusMonths(4));
            }
            else if(tasa.getPeriodo()==7){
                days = DAYS.between(dateBefore,dateBefore.plusMonths(6));
            }else {
                days = DAYS.between(dateBefore,dateBefore.plusMonths(12));
            }


            return valorFuturoEfectivo(deuda.getMonto(),tasa.getMonto(),daysBetween,days)-deuda.getMonto();
        }
    }

    public void updateDeudaInteres(Deuda deuda, Tasa tasa, LocalDateTime date){

        deuda.setInteres(Math.round(genInterest(deuda,tasa,date)*100f)/100f);

        System.out.println("Interes generado: "+deuda.getInteres());
        //deudaRepository.save(deuda);
    }

    public void updateDeuda(Deuda deuda){


    deudaRepository.save(deuda);

    }
}
