package com.lsl.wanandroid.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.Articles;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Create by lsl on 2020/6/14
 */
public class SimpleArticleAdapter extends BaseQuickAdapter<Articles, BaseViewHolder> implements LoadMoreModule {
    public SimpleArticleAdapter(int layoutResId, @Nullable List<Articles> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Articles articles) {
        baseViewHolder.setText(R.id.tv_shareUser, TextUtils.isEmpty(articles.getShareUser()) ? TextUtils.isEmpty(articles.getAuthor()) ? "匿名" :
                articles.getAuthor() : articles.getShareUser());
        baseViewHolder.setText(R.id.tv_title, articles.getTitle());
        baseViewHolder.setText(R.id.tv_niceDate,articles.getNiceDate());
        if (articles.isCollect()) {
            baseViewHolder.setImageResource(R.id.image_Collect, R.drawable.ic_collect_select);
        } else {
            baseViewHolder.setImageResource(R.id.image_Collect, R.drawable.ic_collect_noselect);
        }
    }
}
