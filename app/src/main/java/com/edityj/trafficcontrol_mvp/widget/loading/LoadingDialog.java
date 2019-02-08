package com.edityj.trafficcontrol_mvp.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edityj.trafficcontrol_mvp.R;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/8
 * description: 简洁漂亮的等待弹出框
 */
public class LoadingDialog extends Dialog {
    private String msgText;

    public LoadingDialog(Context context) {
        super(context);
        this.msgText="Loading...";
    }
    public LoadingDialog(Context context,String msgText) {
        super(context);
        this.msgText=msgText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉默认的title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉白色边角 我的小米手机在xml里设置 android:background="@android:color/transparent"居然不生效
        //所以在代码里设置，不知道是不是小米手机的原因
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.load);
        Log.i("LHD", "LoadingDialog onCreate");
        TextView tv = findViewById(R.id.tv);
        tv.setText(msgText);
        LinearLayout linearLayout = this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }
}
