package com.lzyer.jdkrmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author lzyer
 * @date 2017/11/30
 */
public class Server {

    public static void main(String[] args) {

        try {
            PersonService personService = new PersonServiceImpl();
            LocateRegistry.createRegistry(9051);
            /*Context context = new InitialContext();
            context.rebind("rmi://127.0.0.1:9051/PersonService", personService);*/
            Naming.rebind("rmi://127.0.0.1:9051/PersonService", personService);
            System.out.println("Service start!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
