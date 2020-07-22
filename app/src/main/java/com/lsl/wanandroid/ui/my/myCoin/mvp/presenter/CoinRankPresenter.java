package com.lsl.wanandroid.ui.my.myCoin.mvp.presenter;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.my.myCoin.mvp.contract.CoinRankContract;
import com.lsl.wanandroid.ui.my.myCoin.mvp.contract.MyCoinContract;
import com.lsl.wanandroid.ui.my.myCoin.mvp.model.CoinRankModel;
import com.lsl.wanandroid.ui.my.myCoin.mvp.model.MyCoinModel;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * Create by lsl on 2020/7/19
 */
public class CoinRankPresenter extends BasePresenter<CoinRankContract.ICoinRankView> implements CoinRankContract.ICoinRankPresenter {
    private CoinRankModel model;

    public CoinRankPresenter() {
        model = new CoinRankModel();
    }

    @Override
    public void getCoinRank(int page, RxAppCompatActivity activity, boolean isLoadMore) {
        model.getCoinRank(page).compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseObserver<BaseResponse<Coin>>() {
            @Override
            public void onSuccess(BaseResponse<Coin> data) {
                getBaseView().onCoinRank(data.getData().getDatas(), isLoadMore);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error, isLoadMore);
            }
        });
    }
}
