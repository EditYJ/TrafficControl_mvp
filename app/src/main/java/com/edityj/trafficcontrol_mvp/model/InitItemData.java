package com.edityj.trafficcontrol_mvp.model;

import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/1/20
 * description: 巧妙的单实例模式，用于加载原始数据，和修改后的数据。
 *              参考资料：https://www.cnblogs.com/dongyu666/p/6971783.html
 */
public class InitItemData {
    private List<ITEMDATA> ItemDatas;
    private String Battery;

    private InitItemData(){
        this.ItemDatas = new ArrayList<>();
        this.Battery="获取失败";
    }

    private static class SingletonInstance {
        private static final InitItemData initItemDataStart = new InitItemData();
        private static final InitItemData initItemData = new InitItemData();
    }

    public static InitItemData getStartInstance() {
        return SingletonInstance.initItemDataStart;
    }

    public static InitItemData getInstance() {
        return SingletonInstance.initItemData;
    }

    public List<ITEMDATA> getInitItemDatas() {
        return ItemDatas;
    }

    public void setInitItemDatas(List<ITEMDATA> ItemDatas) {
        this.ItemDatas = ItemDatas;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }
}
