package com.example.baserecyclerviewadapterhelperdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.baserecyclerviewadapterhelperdemo.multidelegate.Model;
import com.example.baserecyclerviewadapterhelperdemo.multidelegate.MultiDelegateAdapter;
import com.example.baserecyclerviewadapterhelperdemo.multidelegate.MyMultipleItem;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * 参考文章：
 * RecyclerView 多布局实现、动态设置布局管理器、StaggeredGridLayoutManager占满一行：https://blog.csdn.net/qq_38287890/article/details/108268994
 * BaseRecyclerViewAdapterHelper: 灵活强大的循环适配器：https://blog.csdn.net/chehec2010/article/details/83902263
 * 开源框架BaseRecyclerViewAdapterHelper使用——RecyclerView万能适配器：https://www.jianshu.com/p/1e20f301272e
 * Android利用BaseRecyclerViewAdapterHelper开源库实现多布局拖拽功能：https://blog.csdn.net/cj641809386/article/details/100170972/
 *
 * */
public class MainActivity extends AppCompatActivity implements OnItemDragListener {

    private RecyclerView recyclerView;
    private List<Model> datas01;
    private List<MyMultipleItem> datas02;
    private MultiDelegateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //模拟的假数据（实际开发中当然是从网络获取数据）
        datas01 = new ArrayList<>();
        Model model;
        for (int i = 0; i < 31; i++) {
            model = new Model();
            model.setTitle("我是第" + i + "条标题");
            model.setContent("第" + i + "条内容");
            datas01.add(model);
        }

        datas02 = new ArrayList<>();
        //这里我是随机给某一条目加载不同的布局
        for (int i = 0; i < 31; i++) {
            if (i % 3 == 0 && i!=30) {
                datas02.add(new MyMultipleItem(MyMultipleItem.THIRD_TYPE, datas01.get(i)));
            } else if (i % 7 == 0) {
                datas02.add(new MyMultipleItem(MyMultipleItem.SECOND_TYPE, null));
            } else if (i == 30) {
                datas02.add(new MyMultipleItem(MyMultipleItem.FOOTER_TYPE, null));
            } else {
                datas02.add(new MyMultipleItem(MyMultipleItem.FIRST_TYPE, null));
            }
        }

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(null);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);


        //创建适配器
        adapter = new MultiDelegateAdapter(datas02);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter) {
            /**
             *
             默认不支持多个不同的 ViewType 之间进行拖拽，如果开发者有所需求：
             重写ItemDragAndSwipeCallback里的onMove()方法，return true即可
             * */
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // 开启某类型的布局可拖拽
//        adapter.enableDragItem(itemTouchHelper, R.id.tv_first, true);
        //开启所有类型的布局可拖拽
        adapter.enableDragItem(itemTouchHelper);
        adapter.setOnItemDragListener(this);

        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {

    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = space;
            outRect.bottom = space;
            outRect.left = space;
            outRect.right = space;
        }
    }

}