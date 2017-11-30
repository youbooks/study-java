package com.lzyer.jdkrmi;

import com.lzyer.model.Person;

import java.rmi.Naming;
import java.util.List;

/**
 * @author lzyer
 * @date 2017/11/30
 */
public class Client {

    public static void main(String[] args) {
        try{
            PersonService personService = (PersonService) Naming.lookup("rmi://127.0.0.1:9051/PersonService");
            List<Person> persons = personService.getPersons();
            for(Person p : persons){
                System.out.println(p);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
