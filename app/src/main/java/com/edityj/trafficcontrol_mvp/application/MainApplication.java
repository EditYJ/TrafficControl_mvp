package com.edityj.trafficcontrol_mvp.application;

import android.app.Application;

import com.edityj.trafficcontrol_mvp.BuildConfig;
import com.edityj.trafficcontrol_mvp.config.ConfigOfApp;
import com.edityj.trafficcontrol_mvp.utils.SocketUtil;
import com.socks.library.KLog;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/1
 * description: 应用 application
 */
public class MainApplication extends Application {
    //日志tag
    private final String TAG = MainApplication.class.getSimpleName();
    //self
    private static MainApplication mainApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication=this;
        //初始化KLog
        KLog.init(BuildConfig.LOG_DEBUG, TAG);
        AppInfo.init();
        //初始化socket工具类
        SocketUtil.sharedCenter().setIpAddress(ConfigOfApp.SERVER_IP);
        SocketUtil.sharedCenter().setPort(ConfigOfApp.SERVER_PORT);
    }

    /**
     * 得到application实例
     * @return mainApplication
     */
    public static MainApplication getAppInstance() {
        return mainApplication;
    }
}
