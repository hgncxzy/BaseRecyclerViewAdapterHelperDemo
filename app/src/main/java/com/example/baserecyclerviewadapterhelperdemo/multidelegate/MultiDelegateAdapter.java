package com.example.baserecyclerviewadapterhelperdemo.multidelegate;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.baserecyclerviewadapterhelperdemo.R;

import java.util.List;

import static com.example.baserecyclerviewadapterhelperdemo.multidelegate.MyMultipleItem.FIRST_TYPE;
/**
 * 使用代理的方式实现多布局
 * */
public class MultiDelegateAdapter extends BaseItemDraggableAdapter<MyMultipleItem, BaseViewHolder> {

    public MultiDelegateAdapter(@Nullable List<MyMultipleItem> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<MyMultipleItem>() {
            @Override
            protected int getItemType(MyMultipleItem entity) {
                //根据你的实体类来判断布局类型
                return entity.getItemType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(FIRST_TYPE, R.layout.first_type_layout)
                .registerItemType(MyMultipleItem.SECOND_TYPE, R.layout.second_type_layout)
                .registerItemType(MyMultipleItem.THIRD_TYPE, R.layout.third_type_layout)
                .registerItemType(MyMultipleItem.FOOTER_TYPE, R.layout.footer_type_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMultipleItem item) {
        switch (helper.getItemViewType()) {
            case FIRST_TYPE:
                helper.setText(R.id.tv_first, "车辆状况");
                break;
            case MyMultipleItem.SECOND_TYPE:
                helper.setText(R.id.tv_second, "空调状况");
                break;
            case MyMultipleItem.THIRD_TYPE:
                helper.setText(R.id.tv_third, "能耗排名");
                break;
            case MyMultipleItem.FOOTER_TYPE:
                helper.setText(R.id.tv_footer, "其他设置");
                break;
        }
    }

    //  解决StaggeredGridLayoutManager占满一行
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == MyMultipleItem.FOOTER_TYPE) { //如果类型为底部就占满一行
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }
}