package com.demai.engine;

import com.notnoop.apns.ApnsDelegate;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.DeliveryError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dear on 16/5/20.
 */
public class ApnsDelegateImpl implements ApnsDelegate {

    private static final Logger logger = LoggerFactory.getLogger(ApnsDelegateImpl.class);


    @Override
    public void messageSent(ApnsNotification apnsNotification, boolean b) {
        logger.info("msgSent successfully with id {}", apnsNotification.getIdentifier());
    }

    @Override
    public void messageSendFailed(ApnsNotification apnsNotification, Throwable throwable) {
        logger.info("msgSentError with id {}", apnsNotification.getIdentifier());
    }

    @Override
    public void connectionClosed(DeliveryError deliveryError, int i) {
        logger.info("connection closed {}",deliveryError);
    }

    @Override
    public void cacheLengthExceeded(int i) {

    }

    @Override
    public void notificationsResent(int i) {
        logger.info("notification resent {}",i);
    }
}
