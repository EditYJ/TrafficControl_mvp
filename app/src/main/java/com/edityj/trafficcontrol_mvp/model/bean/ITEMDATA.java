package com.edityj.trafficcontrol_mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/1
 * description: 列表项实体类，实现MultiItemEntity接口，用于对应的列表数据安放至对应的布局中
 */
public class ITEMDATA implements MultiItemEntity {

    //列表布局判断
    public final static int DANGER_TEXT = 1;
    public final static int REMIND_TEXT = 2;
    public final static int SPEED_TEXT = 3;
    public final static int ICON_PIC = 4;

    //列表布局判断依据
    private int itemType;

    //列表对应内容
    private String danger;
    private String remind;
    private String speed;
    private int icon;
    private int iconID;

    public ITEMDATA(){
        this.itemType=0;
        this.danger=null;
        this.remind=null;
        this.speed=null;
        this.icon=0;
        this.iconID=0;
    }

    public ITEMDATA(int itemType) {
        this.itemType = itemType;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
