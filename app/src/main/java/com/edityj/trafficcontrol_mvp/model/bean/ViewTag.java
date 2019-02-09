package com.edityj.trafficcontrol_mvp.model.bean;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/5
 * description: ViewTag's bean
 *              待用
 */
public class ViewTag {
    private int type;
    private int picID;

    public ViewTag(int type) {
        this.type = type;
        this.picID = 0;
    }

    public ViewTag(int type, int picID) {
        this.type = type;
        this.picID = picID;
    }

    public int getType() {
        return type;
    }

    public int getPicID() {
        return picID;
    }
}
