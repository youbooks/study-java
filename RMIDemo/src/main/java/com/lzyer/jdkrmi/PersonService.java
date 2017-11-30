package com.lzyer.jdkrmi;

import com.lzyer.model.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author lzyer
 * @since 2017/11/30
 * @version 0.0.1
 */
public interface PersonService extends Remote {

    List<Person> getPersons()throws RemoteException;
}
