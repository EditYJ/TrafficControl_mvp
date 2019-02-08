package com.edityj.trafficcontrol_mvp.presenter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.edityj.trafficcontrol_mvp.adapter.MyListAdapter;
import com.edityj.trafficcontrol_mvp.application.AppInfo;
import com.edityj.trafficcontrol_mvp.config.ConfigOfApp;
import com.edityj.trafficcontrol_mvp.model.InitItemData;
import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;
import com.edityj.trafficcontrol_mvp.presenter.base.BasePresenter;
import com.edityj.trafficcontrol_mvp.utils.SocketUtil;
import com.edityj.trafficcontrol_mvp.utils.ToastUtil;
import com.edityj.trafficcontrol_mvp.view.MainView;

import java.io.IOException;
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

    public void saveStatus() {
        AppInfo.Sp.setListToJosn(ConfigOfApp.APP_LIST_DATA, getChangeData());
    }

    public void resetData() {
        getChangeData().clear();
        getChangeData().addAll(getStartData());
        saveStatus();
    }

    public void addRemindContent(MyListAdapter myListAdapter, String remindText, int position) {
        ITEMDATA itemdata = new ITEMDATA();
        itemdata.setRemind(remindText);
        itemdata.setItemType(ITEMDATA.REMIND_TEXT);
        myListAdapter.addData(position,itemdata);
        saveStatus();
    }

    public void getData(final String params){
        if (!isAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        SocketUtil.sharedCenter().setTcpCallback(new SocketUtil.TcpCallback() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }

            @Override
            public void onSuccess(String receicedMessage) {
                getView().showToast(receicedMessage);
            }

            @Override
            public void onFailure(IOException e) {
                getView().showError();
            }
        });
        SocketUtil.sharedCenter().connect();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SocketUtil.sharedCenter().send(params.getBytes());
            }
        }, 1000);

    }
}
