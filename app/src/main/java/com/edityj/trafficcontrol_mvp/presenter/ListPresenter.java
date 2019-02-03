package com.edityj.trafficcontrol_mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.edityj.trafficcontrol_mvp.application.AppInfo;
import com.edityj.trafficcontrol_mvp.config.ConfigOfApp;
import com.edityj.trafficcontrol_mvp.model.InitItemData;
import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;
import com.edityj.trafficcontrol_mvp.presenter.base.BasePresenter;
import com.edityj.trafficcontrol_mvp.view.MainView;

import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: 列表相关数据的获取，通知列表做相应的改变
 * TODO 要做的是通知列表界面做相关跟新，主界面要实现列表更新相关函数
 */

public class ListPresenter extends BasePresenter<MainView> {

    public List<ITEMDATA> getStartData(){
        return InitItemData.getStartInstance().getInitItemDatas();
    }

    public List<ITEMDATA> getChangeData(){
        return InitItemData.getInstance().getInitItemDatas();
    }

    public void delSessionData(List<ITEMDATA> list){
//        getChangeData().remove(pos);
            AppInfo.Sp.setListToJosn(ConfigOfApp.APP_LIST_DATA, list);
    }
}
