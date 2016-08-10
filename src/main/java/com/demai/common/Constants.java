package com.demai.common;

/**
 * Created by dear on 16/5/11.
 */
public class Constants {


    public final static String IOS_TOPIC = "com.jiyu.talk";


    public class NoticeSetIsPush {
        public static final int YES = 1;
        public static final int NO = 0;
    }

    public enum PhoneType {
        Android, Apple
    }

    /**
     * redis缓存时间（单位秒）
     *
     * @author qianchun
     * @date 2015年11月19日 上午11:43:37
     */
    public class RedisTimeout {
        public static final int SECONDS_FIVE = 5;
        public static final int SECONDS_TEN = 10;
        public static final int SECONDS_FIFTEEN = 15;
        public static final int SECOND_THIRTY = 30;

        public static final int MINUTES_TEN = 10 * 60;
        public static final int MINUTES_FIFTEEN = 15 * 60;
        public static final int MINUTES_THIRTY = 30 * 60;

        public static final int HOURS_ONE = 1 * 60 * 60;
        public static final int HOURS_TWO = 2 * 60 * 60;
        public static final int HOURS_THREE = 30 * 60;
        public static final int HOURS_SIX = 6 * 60 * 60;
        public static final int HOURS_TEN = 10 * 60 * 60;
        public static final int HOURS_TWELVE = 12 * 60 * 60;
        public static final int HOURS_EIGHTEEN = 18 * 60 * 60;

        public static final int DAY_ONE = 1 * 24 * 60 * 60;
        public static final int DAY_TWO = 2 * 24 * 60 * 60;
        public static final int DAY_THREE = 3 * 24 * 60 * 60;
        public static final int DAY_SEVEN = 7 * 24 * 60 * 60;
    }

    //耗时日志模板
    public static final String LOG_MQ_$MQTYPE_$MQMSG_$LOGMSG = "MQ消息日志【type:{}】：【msg:{}】【error:{}】";
    public static final String MQ_MSG_TYPE_SYSTEM = "系统通知";
    public static final String MQ_MSG_TYPE_CHAT = "聊天通知";

    /**
     * 消息类型常量
     *
     * @author qianchun
     * @date 2016年5月16日 上午11:39:03
     */
    public class MsgType {
        public static final int CHAT_POINT = 1;
        public static final int CHAT_GROUP_ALL = 2;
        public static final int CHAT_GROUP_ADMIN = 3;
        public static final int CHAT_GROUP_USERS = 4;
        public static final int FEED_COMMON = 5;
        public static final int FEED_JOIN = 6;

        public static final int SYSTEM_UIDS = 101;
        public static final int SYSTEM_VERSION = 102;
    }

    /**
     * 设备类型
     *
     * @author qianchun
     * @date 2016年5月16日 下午6:55:24
     */
    public class DeviceMode {
        public static final int IOS = 1;
        public static final int ANDROID = 2;
    }

    /**
     * 群成员类型
     *
     * @author qianchun
     * @date 2016年5月16日 下午7:20:33
     */
    public class MembersFlag {
        public static final int CREATOR = 0;// 0 圈子创建者
        public static final int ADMIN = 1;//1 圈子管理员
        public static final int MEMBER = 2;//2圈子成员
        public static final int NOT_ENTER = -1;//-1未审核
        public static final int EXIT_ACTIVE = -2;//-2 已主动退出圈
        public static final int EXIT_PASSIVE = -3;//-3已被踢出圈
    }

    public static final String STRING_SPLIT_COMMA = ",";//逗号分隔

    public class AckCode {
        public static final int SUCCESS = 1;
        public static final int ERROR = 2;
        public static final int ERROR_FORMAT = 3;
    }

    public class MsgStatus {
        public static final int SUCCESS = 1;
        public static final int FAILURE = 0;
    }

    public class NoticePushStatus {
        public static final int SUCCESS = 1;
        public static final int FORMAT_ERROR = -1;
        public static final int FAILURE = 0;
    }

    /**
     * 版本操作符
     *
     * @author qianchun
     * @date 2016年5月17日 下午3:01:31
     */
    public class VersionOperate {
        public static final int EQ = 0;// 等于
        public static final int LT = 1;// 小于
        public static final int GT = 2;// 大于
        public static final int LTE = 3;// 小于等于
        public static final int GTE = 4;// 大于等于
    }

    /**
     * 用户是否接收群消息
     *
     * @author qianchun
     * @date 2016年5月18日 下午7:29:39
     */
    public class CircleReceiveMsg {
        public static final int YES = 0;//接收
        public static final int NO = -1;//拒收

    }

    public class ChatPointShield {
        public static final int YES = 1;//接收
        public static final int NO = 0;//拒收
    }

    public static final int TIME_MINUTE_1_TO_MILLIS = 1000 * 60;
    public static final int TIME_MINUTE_5_TO_MILLIS = 1000 * 60 * 5;
    public static final int TIME_MINUTE_10_TO_MILLIS = 1000 * 60 * 10;
    public static final int TIME_MINUTE_15_TO_MILLIS = 1000 * 60 * 15;
    public static final int TIME_MINUTE_30_TO_MILLIS = 1000 * 60 * 30;

}
