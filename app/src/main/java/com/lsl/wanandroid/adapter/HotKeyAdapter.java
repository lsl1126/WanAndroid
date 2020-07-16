package com.lsl.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.HotKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Create by lsl on 2020/7/7
 */
public class HotKeyAdapter extends BaseQuickAdapter<HotKey, BaseViewHolder> {
    public HotKeyAdapter(int layoutResId, @Nullable List<HotKey> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HotKey hotKey) {
        baseViewHolder.setText(R.id.tv_Text, hotKey.getName());
    }
}
