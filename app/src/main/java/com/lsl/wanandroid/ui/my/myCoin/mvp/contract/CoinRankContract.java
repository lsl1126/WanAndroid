package com.lsl.wanandroid.ui.my.myCoin.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * Create by lsl on 2020/7/19
 */
public class CoinRankContract {
    public interface ICoinRankView extends IBaseView {
        void onCoinRank(List<Coin.DatasBean> list, boolean isLoadMore);

        void onError(String error, boolean isLoadMore);
    }

    public interface ICoinRankPresenter {
        //积分排行
        void getCoinRank(int page, RxAppCompatActivity activity, boolean isLoadMore);
    }
}
