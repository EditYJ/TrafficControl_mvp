package com.edityj.trafficcontrol_mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.edityj.trafficcontrol_mvp.application.MainApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/1
 * description: 处理SharePreferences的工具方法
 */
public class SharePrefsHelper {
    private String prefName;
    private int mode;

    /**
     * 构造函数
     * 默认sharedPreference模式：私有
     *
     * @param prefName sharedPreference文件的名称
     */
    public SharePrefsHelper(String prefName) {
        this.prefName = prefName;
        this.mode = Context.MODE_PRIVATE;
    }

    /**
     * 构造函数
     * 自定义sharedPreference模式
     *
     * @param prefName sharedPreference文件的名称
     * @param mode     参考Context的mode(MODE_PRIVATE MODE_WORLD_READABLE
     *                 MODE_WORLD_WRITEABLE )
     */
    public SharePrefsHelper(String prefName, int mode) {
        this.prefName = prefName;
        this.mode = mode;
    }

    public void clearAllValues() {
        synchronized (this) {
            getEditor().clear().commit();
        }
    }

    public String getString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public boolean setString(String key, String value) {
        return getEditor().putString(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public boolean setInt(String key, int value) {
        return getEditor().putInt(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public boolean setLong(String key, long value) {
        return getEditor().putLong(key, value).commit();
    }

    public float getFloat(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public boolean setFloat(String key, float value) {
        return getEditor().putFloat(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public boolean setBoolean(String key, boolean value) {
        return getEditor().putBoolean(key, value).commit();
    }

    /**
     * List转JSON存入文件
     * @param key 键
     * @param list 存入的数组
     */
    public void setListToJosn(String key, List list){
        String json = JSON.toJSONString(list);
        getEditor().putString(key,json).commit();
    }

//    /**
//     * 取出字符串信息，转换成list
//     * @param key 键
//     * @param clazz 实体类
//     * @return list<clazz>
//     */
//    public List getJosnToList(String key, Class clazz){
//        String json = getPreferences().getString(key, "");
//        return JSON.parseArray(json, clazz);
//    }

    /**
     * 类的序列化存入文件
     *
     * @param key 键
     * @param obj 存入的类
     *            必须实现Serializable接口，否则会出问题
     */
    public void setBean(String key, Object obj) {
        if (obj instanceof Serializable) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                getEditor().putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }
    }

    /**
     * 反序列化得到对应的类
     * @param key 键
     * @param defValue 默认返回类
     * @return obj
     */
    public Object getBean(String key, Object defValue) {
        Object obj = null;
        try {
            String base64 = getPreferences().getString(key, "");
            if (base64.equals("")) {
                return defValue;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 获取SharedPreferences对象
     *
     * @return SharedPreferences
     */
    private SharedPreferences getPreferences() {
        return MainApplication.getAppInstance().getSharedPreferences(prefName, mode);
    }

    /**
     * 获取SharedPreferences.Editor对象
     *
     * @return edit
     */
    private SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }
}
