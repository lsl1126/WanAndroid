package com.lsl.wanandroid.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.Navigation;
import com.lsl.wanandroid.utils.DisplayUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by lsl on 2020/7/8/008.
 */
public class NavigationAdapter extends BaseQuickAdapter<Navigation, BaseViewHolder> {

//    private final int dp5;
//    private final int dp12;

    public NavigationAdapter(int layoutResId, @Nullable List<Navigation> data) {
        super(layoutResId, data);
//        dp5 = DisplayUtils.dip2px(getContext(), 5);
//        dp12 = DisplayUtils.dip2px(getContext(), 12);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Navigation navigation) {
        baseViewHolder.setText(R.id.tv_Name, navigation.getName());
        TagFlowLayout flowLayout = baseViewHolder.findView(R.id.flowLayout);
        flowLayout.setAdapter(new TagAdapter(navigation.getArticles()) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = new TextView(getContext());
                textView.setBackgroundResource(R.drawable.select_tab);
                textView.setPadding(30, 10, 30, 10);
                textView.setText(navigation.getArticles().get(position).getTitle());
                return textView;
            }
        });
    }
}
