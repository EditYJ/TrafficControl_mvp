package com.edityj.trafficcontrol_mvp.view;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.edityj.trafficcontrol_mvp.R;
import com.edityj.trafficcontrol_mvp.adapter.MyListAdapter;
import com.edityj.trafficcontrol_mvp.presenter.ListPresenter;
import com.edityj.trafficcontrol_mvp.presenter.base.BasePresenter;
import com.edityj.trafficcontrol_mvp.view.base.BaseView;
import com.socks.library.KLog;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/2
 * description: 主视图
 */
public class MainView extends BaseView implements IMainView{
    private ListPresenter listPresenter;

    private MyListAdapter myListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initBaseListConfig();
        addListClickListener();
        recyclerView.setAdapter(myListAdapter);
    }

    /**
     * 初始化列表基本配置
     */
    public void initBaseListConfig(){
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

//        //开启滑动删除等功能
//        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myListAdapter);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
//            //滑动开始时
//            @Override
//            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                KLog.e(listPresenter.getChangeData().size());
//            }
//            //列表元素消失后
//            @Override
//            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
//                KLog.e(listPresenter.getChangeData().size());
//                listPresenter.delSessionData(listPresenter.getChangeData());
//            }
//            //滑动后,列表不一定消失
//            @Override
//            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
//                //删除缓存数据
//                KLog.e(listPresenter.getChangeData().size());
//            }
//            //滑动时
//            @Override
//            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
//                //KLog.e(listPresenter.getChangeData().size());
//            }
//        };
//        // 开启滑动删除
//        myListAdapter.enableSwipeItem();
//        myListAdapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    /**
     * 添加列表相关点击事件
     */
    public void addListClickListener(){
        //点击事件
        myListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainView.this,"点击位置："+position, LENGTH_SHORT).show();
            }
        });

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

    //重置
    public void reset(View view) {
        Toast.makeText(MainView.this,"重置", LENGTH_SHORT).show();
    }

    //编辑
    public void editer(View view) {
        Toast.makeText(MainView.this,"编辑", LENGTH_SHORT).show();
    }

    //清空
    public void clear(View view) {
        Toast.makeText(MainView.this,"清空", LENGTH_SHORT).show();
    }

    //发送
    public void send(View view) {
        Toast.makeText(MainView.this,"发送", LENGTH_SHORT).show();
    }

    //添加
    public void add(View view) {
        Toast.makeText(MainView.this,"添加", LENGTH_SHORT).show();
    }
}
