package com.lzyer.springrmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author lzyer
 * @date 2017/11/30
 */
public class RmiServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmiServer.class);

    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("spring-server.xml");
        Object lock = new Object();
        synchronized (lock){
            LOGGER.debug("Server start Port:{}", 9051);
            lock.wait();
        }
    }
}
