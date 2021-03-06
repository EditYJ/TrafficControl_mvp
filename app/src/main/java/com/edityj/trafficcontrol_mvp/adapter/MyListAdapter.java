package com.edityj.trafficcontrol_mvp.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.edityj.trafficcontrol_mvp.R;
import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;

import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: 列表adapter 支持滑动删除
 */
public class MyListAdapter extends BaseItemDraggableAdapter<ITEMDATA, BaseViewHolder> {
    public MyListAdapter(List<ITEMDATA> data) {
        super(data);
        //设置实体类
        setMultiTypeDelegate(new MultiTypeDelegate<ITEMDATA>() {
            @Override
            protected int getItemType(ITEMDATA itemdata) {
                return itemdata.getItemType();
            }
        });
        //指定类型对应的编号
        getMultiTypeDelegate()
                .registerItemType(ITEMDATA.DANGER_TEXT,R.layout.warning_item)
                .registerItemType(ITEMDATA.REMIND_TEXT,R.layout.remind_item)
                .registerItemType(ITEMDATA.SPEED_TEXT,R.layout.speed_item)
                .registerItemType(ITEMDATA.ICON_PIC,R.layout.icon_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ITEMDATA item) {
        helper.setImageResource(R.id.img_move_icon, item.getMoveIcon());
        if(item.getMoveIcon()==0){
            helper.getView(R.id.img_move_icon).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.img_move_icon).setVisibility(View.VISIBLE);
        }
        //找到item上某个控件，并且给它赋值
        switch (helper.getItemViewType()) {
            case ITEMDATA.DANGER_TEXT:
                helper.setText(R.id.text_danger, item.getDanger());
                break;
            case ITEMDATA.REMIND_TEXT:
                helper.setText(R.id.text_remind, item.getRemind());
                helper.setImageResource(R.id.img_move_icon, item.getMoveIcon());
                helper.setImageResource(R.id.img_delete_icon, item.getDeleteIcon());
                if(item.getDeleteIcon()==0){
                    helper.getView(R.id.img_delete_icon).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.img_delete_icon).setVisibility(View.VISIBLE);
                    helper.addOnClickListener(R.id.img_delete_icon);
                }
                break;
            case ITEMDATA.SPEED_TEXT:
                helper.setText(R.id.text_speed,item.getSpeed());
                helper.setImageResource(R.id.img_move_icon, item.getMoveIcon());
                break;
            case ITEMDATA.ICON_PIC:
                helper.setImageResource(R.id.im_traffic_icon, item.getIcon());
                helper.setImageResource(R.id.img_move_icon, item.getMoveIcon());
                break;
        }


    }


}
