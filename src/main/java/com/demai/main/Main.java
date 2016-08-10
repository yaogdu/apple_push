package com.demai.main;

import com.demai.rabbitmq.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{
                "spring/applicationContext.xml", "spring/applicationContext-db.xml",
                "spring/applicationContext-redis.xml"});
        try {
            Receiver receiver = (Receiver) appContext.getBean("receiver");
            receiver.run();

        } catch (Exception e) {
            logger.info("error", e);
            System.exit(-1);
        }


    }
}
