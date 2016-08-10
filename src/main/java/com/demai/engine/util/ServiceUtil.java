package com.demai.engine.util;

import com.demai.common.Config;
import com.demai.engine.ApnsDelegateImpl;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ReconnectPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.Executors;

/**
 * Created by dear on 16/5/11.
 */
public class ServiceUtil {

    private final static Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

    public static ApnsService appleService;

    public final static Object AndroidService = "";


    static {
        initAppleService();
    }

    private static void initAppleService() {
        logger.info("init appleServiceHolder get appleservice");

        Boolean isProduction = Boolean.getBoolean(Config.get("env.product"));
        logger.info("is product env ? {}", Boolean.toString(isProduction));

        String fullFilePath = ServiceUtil.class.getClassLoader().getResource("").getPath() + File.separator +
                Config.get("apple.encrypted.file");

        logger.info("file path is {}", fullFilePath);

        String pwd = Config.get("apple.pwd");

        try {

//            new ScheduledThreadPoolExecutor(1, threadFactory != null ? threadFactory : defaultThreadFactory())

            appleService = APNS.newService().withReadTimeout(5000).withReconnectPolicy(ReconnectPolicy.Provided
                    .EVERY_HALF_HOUR).withConnectTimeout(30000).asBatched(1, 2)
                    .asPool(8)
                    .withCert(fullFilePath, pwd).withAppleDestination(isProduction)
                    .withDelegate(new
                            ApnsDelegateImpl()).withCacheLength(1000)
                    .build();

//            if (!) {//develop env
//                appleService = APNS.newService().withReadTimeout(5000).withReconnectPolicy(ReconnectPolicy.Provided
//                        .EVERY_HALF_HOUR).withConnectTimeout(30000).asBatched().asPool(10)
//                        .withCert(fullFilePath, pwd)
//                        .withSandboxDestination().withAppleDestination(true).withDelegate(new
//                                ApnsDelegateImpl()).withCacheLength(1000)
//                        .build();
//                logger.info("develop env");
//            } else if ("true".equals(b)) {//product env
//                appleService = APNS.newService().withReadTimeout(5000).withReconnectPolicy(ReconnectPolicy.Provided
//                        .EVERY_HALF_HOUR).withConnectTimeout(30000).asBatched().asPool(10)
//                        .withCert(fullFilePath, pwd)
//                        .withProductionDestination().withAppleDestination(true).withNoErrorDetection()
//                        .build();
//                logger.info("product env");
//            } else {
//                appleService = APNS.newService().withReadTimeout(5000).withReconnectPolicy(ReconnectPolicy.Provided
//                        .EVERY_HALF_HOUR).withConnectTimeout(30000).asBatched().asPool(10)
//                        .withCert(fullFilePath, pwd)
//                        .withSandboxDestination().withAppleDestination(true).withNoErrorDetection()
//                        .build();
//                logger.info("default env");
//            }

            logger.info("env init successfully");
        } catch (Exception e) {
            logger.error("error occurred", e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
