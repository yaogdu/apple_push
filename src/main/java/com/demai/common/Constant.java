package com.demai.common;

/**
 * 常量
 * 
 * @author qianchun
 * @date 2015年11月19日 上午11:06:19
 */
public class Constant {

//    /**
//     * 请求状态
//     * 
//     * @author qianchun
//     * @date 2015年11月19日 上午11:06:45
//     */
//    public class RequestStatus {
//        public static final String SUCCESS_CODE = "0";
//        public static final String PARAMS_ERROR_CODE = "1001";
//        public static final String SYSTEM_ERROR_CODE = "1002";
//        public static final String GROUP_MSG_NO_CODE = "1004";
//        public static final String SYSTEM_NOT_LOGIN_CODE = "1005";
//        public static final String SYSTEM_USER_NO_RIGHT_CODE = "1006";
//
//        public static final String SUCCESS_MSG = "成功";
//        public static final String PARAMS_ERROR_MSG = "参数不正确";
//        public static final String SYSTEM_ERROR_MSG = "系统错误";
//        public static final String GROUP_MSG_NO_MSG = "目前没有群聊信息";
//        public static final String SYSTEM_NOT_LOGIN_MSG = "请先登录";
//        public static final String SYSTEM_USER_NO_RIGHT_MSG = "该用户没权限";
//    }

//    /**
//     * redis缓存时间（单位秒）
//     * 
//     * @author qianchun
//     * @date 2015年11月19日 上午11:43:37
//     */
//    public class RedisTimeout {
//        public static final int SECONDS_FIVE = 5;
//        public static final int SECONDS_TEN = 10;
//        public static final int SECONDS_FIFTEEN = 15;
//        public static final int SECOND_THIRTY = 30;
//
//        public static final int MINUTES_TEN = 10 * 60;
//        public static final int MINUTES_FIFTEEN = 15 * 60;
//        public static final int MINUTES_THIRTY = 30 * 60;
//
//        public static final int HOURS_ONE = 1 * 60 * 60;
//        public static final int HOURS_TWO = 2 * 60 * 60;
//        public static final int HOURS_THREE = 30 * 60;
//        public static final int HOURS_SIX = 6 * 60 * 60;
//        public static final int HOURS_TEN = 10 * 60 * 60;
//        public static final int HOURS_TWELVE = 12 * 60 * 60;
//        public static final int HOURS_EIGHTEEN = 18 * 60 * 60;
//
//        public static final int DAY_ONE = 1 * 24 * 60 * 60;
//        public static final int DAY_TWO = 2 * 24 * 60 * 60;
//        public static final int DAY_THREE = 3 * 24 * 60 * 60;
//        public static final int DAY_SEVEN = 7 * 24 * 60 * 60;
//    }
//    //耗时日志模板
//    public static final String LOG_MQ_$MQTYPE_$MQMSG_$LOGMSG = "MQ消息日志【type:{}】：【msg:{}】【error:{}】";
//    public static final String MQ_MSG_TYPE_SYSTEM = "系统通知";
//    public static final String MQ_MSG_TYPE_CHAT = "聊天通知";
//    
//    /**
//     * 消息类型常量
//     * 
//     * @author qianchun
//     * @date 2016年5月16日 上午11:39:03
//     */
//    public class MsgType {
//    	public static final int CHAT_POINT = 1;
//    	public static final int CHAT_GROUP_ALL = 2;
//    	public static final int CHAT_GROUP_ADMIN = 3;
//    	public static final int CHAT_GROUP_ONE_USER = 4;
//    	
//
//    	public static final int SYSTEM_UIDS = 101;
//    	public static final int SYSTEM_VERSION = 102;
//    	
//    }


    public class QuestionIsAnswered {
        public static final int YES = 1;
        public static final int NO = 0;
    }

    public class QuestionIsDeleted {
        public static final int YES = 1;
        public static final int NO = 0;
    }

    public class NoticeType {
        public static final int FAVORITE_UPDATE_QUESTION = 0;//关注的问题集更新了问题
        public static final int FOLLOWED_USER_QUESTION_ANSWERED = 1;//关注的人提出问题且被人回答
        public static final int YOUR_QUESTION_ANSWERED = 2;//他人回答了你的问题
        public static final int ANOTHER_RAISE_QUESTION_TO_YOU = 3;//他要向你提出问题
        public static final int FOLLOWED_USER_REWARD_QUESTION = 4;//关注的人打赏了问题
        public static final int FOLLOWED_USER_REWARD_FAVORITE = 5;//关注的人打赏了问题集
        public static final int FOLLOWED_USER_SUB_FAVORITE = 6;//关注的人订阅了问题集
        public static final int FOLLOWED_USER_CREATE_FAVORITE = 7;//关注的人创建问题集
        public static final int FOLLOWED_USER_FOLLOWED_ANOTHER = 8;//关注的人关注了他人
        public static final int FOLLOWED_USER_ANSWERED_QUESTION = 9;//关注的人回答了问题
        public static final int FOLLOWED_USER_LIKED_QUESTION = 10;//关注的人赞了问题
    }


}
