package com.demai.util;

import com.demai.engine.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dear on 16/5/13.
 */

@Component
public class AppleFeedBackUtil {

    private static final Logger logger = LoggerFactory.getLogger(AppleFeedBackUtil.class);

    @Resource
    IDeviceService deviceService;

    public void feedBackDevices() {
        try {
            Map<String, Date> inactiveDevices = ServiceUtil.appleService.getInactiveDevices();

            List<String> devices = new ArrayList<>();

            for (String deviceToken : inactiveDevices.keySet()) {
                devices.add(deviceToken);
            }

            //deviceService.updateDeviceId(devices);

            logger.info("feedBackService updated device id with {}", devices);
        } catch (Exception e) {
            logger.error("feedBackDevices error", e);
        }

    }

}
