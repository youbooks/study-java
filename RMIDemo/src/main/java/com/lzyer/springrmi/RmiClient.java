package com.lzyer.springrmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lzyer
 * @date 2017/11/30
 */
public class RmiClient {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        AddService service = (AddService)context.getBean("clientAddService");
        int result = service.getAdd(10,20);
        System.out.println("result: "+result);
    }
}
