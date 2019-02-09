package com.edityj.trafficcontrol_mvp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.edityj.trafficcontrol_mvp.R;
import com.edityj.trafficcontrol_mvp.adapter.MyListAdapter;
import com.edityj.trafficcontrol_mvp.config.ConfigOfApp;
import com.edityj.trafficcontrol_mvp.model.bean.ITEMDATA;
import com.edityj.trafficcontrol_mvp.model.bean.ViewTag;
import com.edityj.trafficcontrol_mvp.presenter.ListPresenter;
import com.edityj.trafficcontrol_mvp.presenter.base.BasePresenter;
import com.edityj.trafficcontrol_mvp.utils.BornView;
import com.edityj.trafficcontrol_mvp.utils.ToastUtil;
import com.edityj.trafficcontrol_mvp.view.base.BaseView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jaeger.library.StatusBarUtil;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.socks.library.KLog;

import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: 主视图
 */
public class MainView extends BaseView implements IMainView {
    private ListPresenter listPresenter;

    private MyListAdapter myListAdapter;
    private RecyclerView recyclerView;

    private RelativeLayout leftScreen;
    private RelativeLayout rightScreen;

    private BornView bornView;

    private boolean isHide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        bornView = new BornView();
        initTitleBar();
        initScreen();
        initBaseListConfig();
        addListClickListener();
        recyclerView.setAdapter(myListAdapter);


    }



    /**
     * 初始化屏幕布局
     */
    public void initScreen() {
        leftScreen = findViewById(R.id.left_screen);
        rightScreen = findViewById(R.id.right_screen);
        leftScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftScreen.removeAllViews();
            }
        });
        rightScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightScreen.removeAllViews();
            }
        });
    }

    /**
     * 初始化上方菜单栏
     */
    public void initTitleBar() {
        //设置状态栏颜色
        //参考资料：https://blog.csdn.net/lixuce1234/article/details/73991906
        StatusBarUtil.setColor(this, 0xbf0b0e);
        // StatusBarUtil.setTransparent(this);
        TitleBar titleBar = findViewById(R.id.topbar);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            //获取电量
            @Override
            public void onLeftClick(View v) {
                String msg=listPresenter.getData(ConfigOfApp.CHECK_BATTERY);
                if(msg==null || msg.length()==0){
//                    ToastUtil.showToast("电量获取失败，请重试");
                }else{
                    String[]  strs=msg.split(" ");
                    msg = strs[0]+ConfigOfApp.BATTERY_DIV;
                    ((TextView)v).setText(msg);
                }
            }

            //中间标题
            @Override
            public void onTitleClick(View v) {
                ToastUtil.showToast("中间View被点击");
            }

            //发送
            @Override
            public void onRightClick(View v) {
                ToastUtil.showToast("右项View被点击");
//                listPresenter.getData("点击你好！");
                String sendMsg = ConfigOfApp.SEND_SCREEN_HEAD;
                View lview = leftScreen.getChildAt(0);
                View rview = rightScreen.getChildAt(0);
                //左屏不为空
                if (lview != null) {
                    ViewTag lviewTag = (ViewTag) lview.getTag();
                    //左屏为警告词段
                    if (lviewTag.getType() == ITEMDATA.DANGER_TEXT) {
                        String danger = listPresenter.getChangeData().get(lviewTag.getPosition()).getDanger();
                        sendMsg += ConfigOfApp.SEND_WARNING_MSG_HEAD + danger + ConfigOfApp.SEND_WARNING_MSG_END;
                        //左屏为图片
                    } else if (lviewTag.getType() == ITEMDATA.ICON_PIC) {
                        int picID = lviewTag.getPicID();
                        sendMsg += picID;
                    } else {
                        String remind = listPresenter.getChangeData().get(lviewTag.getPosition()).getRemind();
                        if (remind==null||remind.length()==0) {
                            remind = listPresenter.getChangeData().get(lviewTag.getPosition()).getSpeed();
                        }
                        sendMsg += remind;
                    }
                    //右屏不为空
                    if(rview != null){
                        ViewTag rviewTag = (ViewTag) rview.getTag();
                        if (rviewTag.getType() == ITEMDATA.ICON_PIC) {
                            int picID = rviewTag.getPicID();
                            sendMsg += ConfigOfApp.SEND_SCREEN_POINT+picID;
                        } else {
                            String remind = listPresenter.getChangeData().get(rviewTag.getPosition()).getRemind();
                            KLog.e(remind);
                            //判断字符串为空的最好方法
                            //参考资料：https://www.cnblogs.com/qiuting/p/5373571.html
                            if (remind==null||remind.length()==0) {
                                remind = listPresenter.getChangeData().get(rviewTag.getPosition()).getSpeed();
                            }
                            sendMsg += ConfigOfApp.SEND_SCREEN_POINT+remind;
                        }
                    }
                    listPresenter.getData(sendMsg);
                    //左屏为空
                } else if(rview != null){
                    ViewTag rviewTag = (ViewTag) rview.getTag();
                    if (rviewTag.getType() == ITEMDATA.ICON_PIC) {
                        int picID = rviewTag.getPicID();
                        sendMsg += picID;
                    } else {
                        String remind = listPresenter.getChangeData().get(rviewTag.getPosition()).getRemind();
                        if (remind==null||remind.length()==0) {
                            remind = listPresenter.getChangeData().get(rviewTag.getPosition()).getSpeed();
                        }
                        sendMsg +=remind;
                    }
                    listPresenter.getData(sendMsg);
                }else{
                    ToastUtil.showToast("屏幕内容为空，请输入内容后在发送");
                }
            }
        });
    }


    /**
     * 初始化列表基本配置
     */
    public void initBaseListConfig() {
        //初始化recyclerView
        recyclerView = findViewById(R.id.list_layout);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainView.this);
        recyclerView.setLayoutManager(layoutManager);
        //设置分割线
        recyclerView.addItemDecoration(getRecyclerViewDivider(R.drawable.list_div_line));
        KLog.e(listPresenter.getChangeData().size());
        KLog.json(JSON.toJSONString(listPresenter.getChangeData()));
        //设置数据
        myListAdapter = new MyListAdapter(listPresenter.getChangeData());
        //设置动画效果
        //ALPHAIN(渐显),SCALEIN(缩放),SLIDEIN_BOTTOM,SLIDEIN_LEFT,SLIDEIN_RIGHT 等等
        myListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    /**
     * 添加列表相关点击事件
     */
    public void addListClickListener() {
        //点击事件
        myListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView textView;
                ImageView imageView;
                int type = listPresenter.getChangeData().get(position).getItemType();
                String danger = listPresenter.getChangeData().get(position).getDanger();
                String remind = listPresenter.getChangeData().get(position).getRemind();
                String speed = listPresenter.getChangeData().get(position).getSpeed();
                int icon = listPresenter.getChangeData().get(position).getIcon();
                int picID = listPresenter.getChangeData().get(position).getIconID();
                int leftScreenChildType = 0;
                if (leftScreen.getChildAt(0) != null) {
                    leftScreenChildType = ((ViewTag) leftScreen.getChildAt(0).getTag()).getType();
                }
                if (type == ITEMDATA.DANGER_TEXT) {
                    leftScreen.removeAllViews();
                    rightScreen.removeAllViews();
                    textView = bornView.getDangerTextView(position);
                    textView.setText(danger);
                    leftScreen.addView(textView, 0);
                    //提示
                } else if (type == ITEMDATA.REMIND_TEXT) {
                    textView = bornView.getRemindTextView(position);
                    textView.setText(remind);
                    if (leftScreen.getChildAt(0) == null) {
                        leftScreen.addView(textView, 0);
                    } else {
                        if (rightScreen.getChildAt(0) == null && leftScreenChildType != ITEMDATA.DANGER_TEXT) {//////////////////<<<<<===============
                            rightScreen.addView(textView, 0);
                        } else {
                            ToastUtil.showToast("请清除内容后再进行设置！");
                        }
                    }
                    //速度
                } else if (type == ITEMDATA.SPEED_TEXT) {
                    textView = bornView.getSpeedTextView(position);
                    textView.setText(speed);
                    if (leftScreen.getChildAt(0) == null) {
                        leftScreen.addView(textView, 0);
                    } else {
                        if (rightScreen.getChildAt(0) == null && leftScreenChildType != ITEMDATA.DANGER_TEXT) {
                            rightScreen.addView(textView, 0);
                        } else {
                            ToastUtil.showToast("请清除内容后再进行设置！");
                        }
                    }
                    //图片
                } else {
                    imageView = bornView.getImageView(position, picID);
                    imageView.setImageResource(icon);
                    if (leftScreen.getChildAt(0) == null) {
                        leftScreen.addView(imageView, 0);
                    } else {
                        if (rightScreen.getChildAt(0) == null && leftScreenChildType != ITEMDATA.DANGER_TEXT) {
                            rightScreen.addView(imageView, 0);
                        } else {
                            ToastUtil.showToast("请清除内容后再进行设置！");
                        }
                    }
                }
            }
        });

        //删除按钮点击事件
        myListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                myListAdapter.remove(position);
            }
        });

        // 开启拖拽
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        OnItemDragListener onItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };
        myListAdapter.enableDragItem(itemTouchHelper, R.id.img_move_icon, true);
        myListAdapter.setOnItemDragListener(onItemDragListener);
    }

    /**
     * 获取分割线
     *
     * @param drawableId 分割线id
     * @return itemDecoration
     */
    public RecyclerView.ItemDecoration getRecyclerViewDivider(@DrawableRes int drawableId) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(drawableId));
        return itemDecoration;
    }

    @Override
    public BasePresenter getPresenter() {
        return listPresenter;
    }

    @Override
    public void initPresenter() {
        listPresenter = new ListPresenter();
    }

    //======================================悬浮菜单点击事件设置===================================//
    //重置
    public void reset(View view) {
        listPresenter.resetData();
        myListAdapter.notifyDataSetChanged();
        ToastUtil.showToast("重置");
    }

    //开启和关闭编辑功能
    public void editer(View view) {
//        ToastUtil.showToast("编辑");
        List<ITEMDATA> list = listPresenter.getChangeData();
        if (isHide) {
            for (int k = 0; k < list.size(); k++) {
                list.get(k).setMoveIcon(R.drawable.move_icon);
                if (list.get(k).getRemind() != null) {
                    list.get(k).setDeleteIcon(R.drawable.delete_icon);
                }
            }
            ((TextView) view).setText("完成");
            isHide = false;
        } else {
            for (int k = 0; k < list.size(); k++) {
                list.get(k).setMoveIcon(0);
                if (list.get(k).getRemind() != null) {
                    list.get(k).setDeleteIcon(0);
                }
            }
            listPresenter.saveStatus();
            ((TextView) view).setText("编辑");
            isHide = true;
        }
        myListAdapter.notifyDataSetChanged();
    }

    //清空
    public void clear(View view) {
        ToastUtil.showToast("清空");
        leftScreen.removeAllViews();
        rightScreen.removeAllViews();
    }

    //添加
    public void add(View view) {
//        ToastUtil.showToast("添加");
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add, null);
        final EditText editText = dialogView.findViewById(R.id.dialog_edit_add);
        mMaterialDialog.setView(dialogView);
        mMaterialDialog.setTitle("这是标题")
//                .setTitleTextColor(R.color.colorPrimary)
//                .setTitleTextSize((float) 22.5)
//                .setMessage("支持所有布局文字大小颜色等设置。")
//                .setMessageTextColor(R.color.colorPrimary)
//                .setMessageTextSize((float) 16.5)
//                .setPositiveButtonTextColor(R.color.colorAccent)
//                .setNegativeButtonTextColor(R.color.colorPrimary)
//                .setPositiveButtonTextSize(15)
//                .setNegativeButtonTextSize(15)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = editText.getText().toString().trim();
                        ToastUtil.showToast(text);
                        listPresenter.addRemindContent(myListAdapter, text, 2);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                .setCanceledOnTouchOutside(true)
                .setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                //对话框消失后回调
                            }
                        })
                .show();

    }

    //调整亮度
    public void changeLight(View view) {
        ToastUtil.showToast("调整亮度");
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_light, null);
        mMaterialDialog.setView(dialogView);
        mMaterialDialog.setTitle("这是标题")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = ((EditText)dialogView.findViewById(R.id.dialog_edit_light)).getText().toString().trim();
                        if(Integer.valueOf(text)<1 || Integer.valueOf(text)>10){
                            ToastUtil.showToast("输入数字超出范围，请重新输入");
                            ((EditText)dialogView.findViewById(R.id.dialog_edit_light)).setText("");
                        }else{
                            mMaterialDialog.dismiss();
                            listPresenter.getData(ConfigOfApp.CHANGE_LIGHT+text);
                        }
                    }
                })
                .setNegativeButton("取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                .setCanceledOnTouchOutside(true)
                .setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                //对话框消失后回调
                            }
                        })
                .show();
    }
}
