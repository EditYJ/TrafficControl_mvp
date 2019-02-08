package com.edityj.trafficcontrol_mvp.config;
/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/1
 * description: 应用内一些公用的变量
 */
public final class ConfigOfApp {
    public final static String SERVER_IP="192.168.31.96";
    public final static int SERVER_PORT=6666;
    public final static String CHAR_SET="GBK";


    public final static String APP_SESSION="TC_session";
    public final static String APP_LIST_RESET_DATA="list_reset_data";   //用于重置
    public final static String APP_LIST_DATA="list_data";   //可变化
    //是否第一次打开app
    public final static String APP_FIRST_FLAG="flag";


    //协议部分
    public final static String SEND_SCREEN_HEAD="2:DISPLAY:";
    public final static String CHECK_BATTERY="1:BATTERY";
    public final static String CHANGE_LIGHT="3:BACKLIGHT:";
    public final static String SEND_SCREEN_POINT=",";
    public final static String SEND_IMG_HEAD="t";
    public final static String SEND_WARNING_MSG_HEAD="$$";
    public final static String SEND_WARNING_MSG_END=",,";
}
