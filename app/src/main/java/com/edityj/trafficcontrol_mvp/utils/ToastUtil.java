package com.edityj.trafficcontrol_mvp.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.edityj.trafficcontrol_mvp.application.MainApplication;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/4
 * description: 土司简单的工具类
 *              参考github：https://github.com/wanglijun93/AndroidUtils
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * show方法
     * @param text 显示的文本
     */
    @SuppressLint("ShowToast")
    public static void showToast(String text){
        if(toast==null){
            toast = Toast.makeText(MainApplication.getAppInstance(), text,Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);//如果不为空，则直接改变当前toast的文本
        }
        toast.show();
    }
}
