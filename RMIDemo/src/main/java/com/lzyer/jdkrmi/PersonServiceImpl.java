package com.lzyer.jdkrmi;

import com.lzyer.model.Person;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzyer
 * @since 2017/11/30
 * @version 0.0.1
 */
public class PersonServiceImpl extends UnicastRemoteObject implements PersonService {

    public PersonServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Person> getPersons() {
        System.out.println("getPersons....");
        List<Person> persons = new ArrayList<>();
        for(int i=0; i<5; i++){
            Person person = new Person(i, "name"+i, i+20);
            persons.add(person);
        }
        return persons;
    }
}
