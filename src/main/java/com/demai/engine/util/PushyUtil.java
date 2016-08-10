package com.demai.engine.util;

import com.demai.common.Config;
import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.ApnsPushNotification;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by dear on 16/5/22.
 */
public class PushyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PushyUtil.class);

    public static ApnsClient<ApnsPushNotification> apnsClient;

    //public static volatile boolean connected = false;

    public static void initAppleService() {


        logger.info("start to initial apple push service");

        String isPro = Config.get("env.product");

        logger.info("is product env ? {}", isPro);

        String fullFilePath = ServiceUtil.class.getClassLoader().getResource("").getPath() + File.separator +
                Config.get("apple.encrypted.file");

        logger.info("file path is {}", fullFilePath);

        String pwd = Config.get("apple.pwd");

        try {

            boolean connected = false;
            while (!connected) {
                apnsClient = new ApnsClient<>(
                        new File(fullFilePath), "123456");

                String host = "true".equals(isPro) ? ApnsClient.PRODUCTION_APNS_HOST : ApnsClient.DEVELOPMENT_APNS_HOST;
                logger.info("host is {}", host);

                final Future<Void> connectFuture = apnsClient.connect(host);

                Future f = connectFuture.await();

                logger.info("apnsClient connected to APNs.");

                logger.info("apple push service initialed successfully");
                connected = true;
            }

        } catch (Exception e) {
            logger.error("error o ccurred", e);
            try {
                initAppleService();
            } catch (Exception e1) {
                logger.info("connection error");
            }

        }
    }

}
