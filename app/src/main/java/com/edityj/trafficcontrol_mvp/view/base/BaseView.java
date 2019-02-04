package com.edityj.trafficcontrol_mvp.view.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.edityj.trafficcontrol_mvp.presenter.base.BasePresenter;
import com.edityj.trafficcontrol_mvp.utils.ToastUtil;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: view顶级父类
 *              抽象类
 */
@SuppressLint("Registered")
public abstract class BaseView extends Activity implements IBaseView{

    /**
     * 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();
    /**
     * 初始化Presenter的实例，子类实现
     */
    public abstract void initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();
        if(getPresenter() !=null){
            getPresenter().attachView(this);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void showError() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑presenter
        getPresenter().detachView();
    }
}
