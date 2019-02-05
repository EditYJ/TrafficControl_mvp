package com.edityj.trafficcontrol_mvp.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edityj.trafficcontrol_mvp.application.MainApplication;
import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;
import com.edityj.trafficcontrol_mvp.model.bean.ViewTag;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/5
 * description: 生产布局
 */
public class BornView {
    private ImageView imageView;
    private TextView DangerTextView;
    private TextView RemindTextView;
    private TextView SpeedTextView;

    public BornView(){
    }

    public ImageView getImageView() {
        imageView = new ImageView(MainApplication.getAppInstance());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DpOrPxUtils.dip2px(MainApplication.getAppInstance(),50),DpOrPxUtils.dip2px(MainApplication.getAppInstance(),50));
        imageView.setLayoutParams(layoutParams);
        imageView.setTag(ITEMDATA.ICON_PIC);
        return imageView;
    }

    public TextView getDangerTextView() {
        DangerTextView = new TextView(MainApplication.getAppInstance());
        DangerTextView.setTextColor(Color.RED);
        DangerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        DangerTextView.setGravity(Gravity.CENTER);
        DangerTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        DangerTextView.setTag(ITEMDATA.DANGER_TEXT);
        return DangerTextView;
    }

    public TextView getRemindTextView() {
        RemindTextView = new TextView(MainApplication.getAppInstance());
        RemindTextView.setTextColor(Color.BLACK);
        RemindTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        RemindTextView.setGravity(Gravity.CENTER);
        RemindTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        RemindTextView.setTag(ITEMDATA.REMIND_TEXT);
        return RemindTextView;
    }

    public TextView getSpeedTextView() {
        SpeedTextView = new TextView(MainApplication.getAppInstance());
        SpeedTextView.setTextColor(Color.BLACK);
        SpeedTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        SpeedTextView.setGravity(Gravity.CENTER);
        SpeedTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        SpeedTextView.setTag(ITEMDATA.SPEED_TEXT);
        return SpeedTextView;
    }
}
