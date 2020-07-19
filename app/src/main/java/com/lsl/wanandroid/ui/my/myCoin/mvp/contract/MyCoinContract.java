package com.lsl.wanandroid.ui.my.myCoin.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * Create by lsl on 2020/7/19
 */
public class MyCoinContract {
    public interface IMyCoinView extends IBaseView {
        void onMyCoin(Coin.DatasBean coin);

        void onMyCoinError(String error);

        void onMyCoinDetails(List<CoinDetail.DatasBean> list,boolean isLoadMore);

        void onMyCoinDetailsError(String error,boolean isLoadMore);
    }

    public interface IMyCoinPresenter {
        //个人积分
        void getMyCoin(RxAppCompatActivity activity);

        //个人积分明细
        void getMyCoinDetails(int page, RxAppCompatActivity activity,boolean isLoadMore);
    }
}
