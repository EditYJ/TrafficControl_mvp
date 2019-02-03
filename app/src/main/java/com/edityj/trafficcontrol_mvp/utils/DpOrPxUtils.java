package com.edityj.trafficcontrol_mvp.utils;

import android.content.Context;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/3
 * description: dp和px相互转换的工具类
 */
public class DpOrPxUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
