package com.edityj.trafficcontrol_mvp.application;

import android.content.res.TypedArray;

import com.alibaba.fastjson.JSON;
import com.edityj.trafficcontrol_mvp.R;
import com.edityj.trafficcontrol_mvp.config.ConfigOfApp;
import com.edityj.trafficcontrol_mvp.model.InitItemData;
import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;
import com.edityj.trafficcontrol_mvp.utils.SharePrefsHelper;
import com.socks.library.KLog;

import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/1
 * description: 应用级信息
 *              初始化列表信息
 */
public class AppInfo {

    private static SharePrefsHelper Sp = null;
    private static MainApplication mainApplication;

    private static TypedArray icon;
    private static String[] speed;
    private static String[] warning;
    private static String[] remind;

    private static List<ITEMDATA> listStart;
    private static List<ITEMDATA> list;

    public static void init() {
        int i;
        mainApplication = MainApplication.getAppInstance();
        listStart = InitItemData.getStartInstance().getInitItemDatas();
        list = InitItemData.getInstance().getInitItemDatas();
        if (Sp == null) {
            Sp = new SharePrefsHelper(ConfigOfApp.APP_SESSION);
        }
        if(Sp.getBoolean(ConfigOfApp.APP_FIRST_FLAG,true)){
            //第一次打开初始化数据
            icon = mainApplication.getResources().obtainTypedArray(R.array.icon);
            speed = mainApplication.getResources().getStringArray(R.array.speed);
            warning = mainApplication.getResources().getStringArray(R.array.warning);
            remind = mainApplication.getResources().getStringArray(R.array.remind);
            //警告类数据==1
            for(i=0;i<warning.length;i++){
                ITEMDATA itemdata=new ITEMDATA(ITEMDATA.DANGER_TEXT);
                itemdata.setDanger(warning[i]);
                list.add(itemdata);
                listStart.add(itemdata);
            }
            //提示类数据==2
            for(i=0;i<remind.length;i++){
                ITEMDATA itemdata=new ITEMDATA(ITEMDATA.REMIND_TEXT);
                itemdata.setRemind(remind[i]);
                list.add(itemdata);
                listStart.add(itemdata);
            }
            //速度信息==3
            for(i=0;i<speed.length;i++){
                ITEMDATA itemdata=new ITEMDATA(ITEMDATA.SPEED_TEXT);
                itemdata.setSpeed(speed[i]);
                list.add(itemdata);
                listStart.add(itemdata);
            }
            //交通图标==4
            for(i=0;i<icon.length();i++){
                ITEMDATA itemdata=new ITEMDATA(ITEMDATA.ICON_PIC);
                itemdata.setIcon(icon.getResourceId(i,0));
                itemdata.setIconID(i+1);
                list.add(itemdata);
                listStart.add(itemdata);
            }
            //放入sharepreference
            Sp.setListToJosn(ConfigOfApp.APP_LIST_DATA, InitItemData.getInstance().getInitItemDatas());
            Sp.setListToJosn(ConfigOfApp.APP_LIST_RESET_DATA, InitItemData.getInstance().getInitItemDatas());
            //设置APP_FIRST_FLAG为false
            Sp.setBoolean(ConfigOfApp.APP_FIRST_FLAG, false);
        }else{
            //注意事项
            //使用fastJSON对应的实体类要有默认传入参数为空的构造方法
            //使用fastJSON对应的实体类内部的所有需要的变量都需要写上get和set方法
            //还有Sp.getString返回的可能不是标准的字符串，得赋值到对应的字符串变量，才能让JSON.parseArray起作用
            String getChangeData = Sp.getString(ConfigOfApp.APP_LIST_DATA,"NONEData");
            String getStartData = Sp.getString(ConfigOfApp.APP_LIST_RESET_DATA,"NONEData");
            KLog.json(getChangeData);
            //第二次打开初始化数据
            list.addAll(JSON.parseArray(getChangeData, ITEMDATA.class));
            //KLog.e(list.size());
            listStart.addAll(JSON.parseArray(getStartData, ITEMDATA.class));
        }
    }
}
