package com.demai.util;

import com.demai.common.Constants;
import com.demai.common.DateUtil;
import com.demai.engine.util.PushyUtil;
import com.demai.entity.PushTask;
import com.relayrides.pushy.apns.ApnsPushNotification;
import com.relayrides.pushy.apns.ClientNotConnectedException;
import com.relayrides.pushy.apns.PushNotificationResponse;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by dear on 16/5/23.
 */
public class BigLog {


    private final static Logger logger = LoggerFactory.getLogger(BigLog.class);

    private static final Logger bgLogger = LoggerFactory.getLogger("bg");

    public static void log(final PushTask pushTask, final Future<PushNotificationResponse<ApnsPushNotification>> future) {
        try {

            while (!PushyUtil.apnsClient.isConnected()) {
                try {
                    PushyUtil.apnsClient.getReconnectionFuture().await();
                    logger.info("reconnected.");
                } catch (Exception e) {
                    logger.info("recovery error", e);
                }
            }

            long now = System.currentTimeMillis();
            final PushNotificationResponse<ApnsPushNotification> response =
                    future.get();
            int result = Constants.MsgStatus.FAILURE;

            if (response.isAccepted()) {
                logger.info("success");
                result = Constants.MsgStatus.SUCCESS;
                //logger.info("send msg to token {} successful", pushTask.token);
            } else {
                logger.info(response.getRejectionReason());
//                logger.info("notification with token {} sent error due to {}", response.getPushNotification()
//                        .getToken(), response.getRejectionReason());
            }


//            bgLogger.info("notice   {}  {}  {}  {}  {}  {}  {}  {}  {}", pushTask.uid, DateUtil.format(new Date(), null),
//                    pushTask.type, pushTask.title, result, System.currentTimeMillis() - now, pushTask.app, pushTask.token, pushTask.appVersion);

            bgLogger.info("notice\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}", pushTask.uid, DateUtil.format(new Date(), null),
                    pushTask.type, pushTask.title, result, System.currentTimeMillis() - now, pushTask.app, pushTask.token, pushTask.appVersion);

        } catch (ClientNotConnectedException cnce) {
            logger.error("client not connected,", cnce);
            while (!PushyUtil.apnsClient.isConnected()) {
                try {
                    PushyUtil.apnsClient.getReconnectionFuture().await();
                    logger.info("reconnected.");
                } catch (Exception e) {
                    logger.info("recovery error", e);
                }
            }
        } catch (Exception e) {
            logger.error("big log error", e);
        }
    }

}


