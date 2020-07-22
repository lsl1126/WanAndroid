package com.lsl.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Create by lsl on 2020/7/19
 */
public class CoinRankAdapter extends BaseQuickAdapter<Coin.DatasBean, BaseViewHolder> implements LoadMoreModule {
    public CoinRankAdapter(int layoutResId, @Nullable List<Coin.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Coin.DatasBean datasBean) {
        if (baseViewHolder.getAdapterPosition() == 0
                || baseViewHolder.getAdapterPosition() == 1
                || baseViewHolder.getAdapterPosition() == 2) {
            baseViewHolder.setGone(R.id.tv_Rank, true);
            baseViewHolder.setVisible(R.id.image_Rank, true);
            switch (baseViewHolder.getAdapterPosition()) {
                case 0:
                    baseViewHolder.setImageResource(R.id.image_Rank, R.drawable.ic_rank_first);
                    break;
                case 1:
                    baseViewHolder.setImageResource(R.id.image_Rank, R.drawable.ic_rank_second);
                    break;
                case 2:
                    baseViewHolder.setImageResource(R.id.image_Rank, R.drawable.ic_rank_third);
                    break;
            }
        } else {
            baseViewHolder.setGone(R.id.image_Rank, true);
            baseViewHolder.setVisible(R.id.tv_Rank, true);
            baseViewHolder.setText(R.id.tv_Rank, String.valueOf(baseViewHolder.getAdapterPosition() + 1));
        }
        baseViewHolder.setText(R.id.tv_UserName, datasBean.getUsername());
        baseViewHolder.setText(R.id.tv_CoinCount, String.valueOf(datasBean.getCoinCount()));
    }
}
