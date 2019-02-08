package com.edityj.trafficcontrol_mvp.view.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.edityj.trafficcontrol_mvp.presenter.base.BasePresenter;
import com.edityj.trafficcontrol_mvp.utils.ToastUtil;
import com.edityj.trafficcontrol_mvp.widget.loading.LoadingDialog;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: view顶级父类
 *              抽象类
 */
@SuppressLint("Registered")
public abstract class BaseView extends Activity implements IBaseView{
    private LoadingDialog progressDialog;
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
        progressDialog = new LoadingDialog(this);
//      仅点击外部不可取消
//      dialog.setCanceledOnTouchOutside(false);
//      点击返回键和外部都不可取消
//      dialog.setCancelable(false);
//      dialog.show();
        progressDialog.setCancelable(false);
        initPresenter();
        if(getPresenter() !=null){
            getPresenter().attachView(this);
        }
    }

    @Override
    public void showLoading() {
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void showError() {
        ToastUtil.showToast("连接出现问题，请检查wifi");
    }

    @Override
    public Context getContext() {
        return BaseView.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑presenter
        getPresenter().detachView();
    }
}
