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
 * Created by lsl on 2020/6/12/012.
 */
public class ArticleAdapter extends BaseQuickAdapter<Articles, BaseViewHolder> implements LoadMoreModule {
    public ArticleAdapter(int layoutResId, @Nullable List<Articles> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Articles articles) {
        baseViewHolder.setText(R.id.tv_shareUser, TextUtils.isEmpty(articles.getShareUser()) ? TextUtils.isEmpty(articles.getAuthor()) ? "匿名" :
                articles.getAuthor() : articles.getShareUser());
        baseViewHolder.setText(R.id.tv_superChapterName, articles.getSuperChapterName());
        baseViewHolder.setText(R.id.tv_title, articles.getTitle());
        baseViewHolder.setText(R.id.tv_niceDate,articles.getNiceDate());
        if (!TextUtils.isEmpty(articles.getDesc())) {
            baseViewHolder.setGone(R.id.tv_desc, false);
            String desc = articles.getDesc()
                    .replace("<pre>", "")
                    .replace("</pre>", "")
                    .replace("<code>", "")
                    .replace("</code>", "")
                    .replace("<p>", "")
                    .replace("</p>", "");
            baseViewHolder.setText(R.id.tv_desc, desc);
        } else {
            baseViewHolder.setGone(R.id.tv_desc, true);
        }
        if (articles.isFresh()) {
            baseViewHolder.setGone(R.id.tv_fresh, false);
        } else {
            baseViewHolder.setGone(R.id.tv_fresh, true);
        }
        if (articles.isTop()) {
            baseViewHolder.setGone(R.id.tv_top, false);
        } else {
            baseViewHolder.setGone(R.id.tv_top, true);
        }
        if (articles.isCollect()) {
            baseViewHolder.setImageResource(R.id.image_Collect, R.drawable.ic_collect_select);
        } else {
            baseViewHolder.setImageResource(R.id.image_Collect, R.drawable.ic_collect_noselect);
        }
    }
}
