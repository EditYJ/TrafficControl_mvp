package com.edityj.trafficcontrol_mvp.view.base;

import android.content.Context;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: 基础视图接口
 */
public interface IBaseView {

    /**
     * 显示正在加载的View
     */
    void showLoading();

    /**
     * 关闭正在加载的View
     */
    void hideLoading();

    /**
     * 显示提示
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showError();

    /**
     * 获取上下文
     * @return context
     */
    Context getContext();
}
