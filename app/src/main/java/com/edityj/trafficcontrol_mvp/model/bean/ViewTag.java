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
    private int position;
    private int picID;

    public ViewTag(int type, int position) {
        this.type = type;
        this.picID = 0;
        this.position = position;
    }

    public ViewTag(int type, int position,int picID) {
        this.type = type;
        this.position = position;
        this.picID = picID;
    }

    public int getType() {
        return type;
    }

    public int getPicID() {
        return picID;
    }

    public int getPosition() {
        return position;
    }
}
