package com.edityj.trafficcontrol_mvp.presenter.base;

import com.edityj.trafficcontrol_mvp.view.base.IBaseView;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: presenter顶级父类
 */
public class BasePresenter<V extends IBaseView> {
    /**
     * 绑定的View
     */
    private V view;

    /**
     * 绑定View
     */
    public void attachView(V view) {
        this.view = view;
    }

    /**
     * 解绑View
     *
     */
    public void detachView() {
        this.view = null;
    }

    /**
     * 判断是否绑定了View
     *
     * @return view != null
     */
    public boolean isAttached() {
        return view != null;
    }

    /**
     * 获取绑定的View
     */
    public V getView() {
        return view;
    }
}
