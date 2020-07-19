package com.lsl.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.CoinDetail;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Create by lsl on 2020/7/19
 */
public class CoinDetailsAdapter extends BaseQuickAdapter<CoinDetail.DatasBean, BaseViewHolder> implements LoadMoreModule {
    public CoinDetailsAdapter(int layoutResId, @Nullable List<CoinDetail.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoinDetail.DatasBean datasBean) {
        baseViewHolder.setText(R.id.tv_Coin, datasBean.getDesc().substring(20)
                .replace(",", "")
                .replace(" ", ""));
        baseViewHolder.setText(R.id.tv_Date, datasBean.getDesc().substring(0, 20));
        baseViewHolder.setText(R.id.tv_CoinCount, "+" + datasBean.getCoinCount());
    }
}
