package com.lsl.wanandroid.ui.my.myCoin.mvp.presenter;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.my.myCoin.mvp.contract.MyCoinContract;
import com.lsl.wanandroid.ui.my.myCoin.mvp.model.MyCoinModel;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * Create by lsl on 2020/7/19
 */
public class MyCoinPresenter extends BasePresenter<MyCoinContract.IMyCoinView> implements MyCoinContract.IMyCoinPresenter {
    private MyCoinModel model;

    public MyCoinPresenter() {
        model = new MyCoinModel();
    }

    @Override
    public void getMyCoin(RxAppCompatActivity activity) {
        model.getMyCoin().compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseObserver<BaseResponse<Coin.DatasBean>>() {
            @Override
            public void onSuccess(BaseResponse<Coin.DatasBean> data) {
                getBaseView().onMyCoin(data.getData());
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onMyCoinError(error);
            }
        });
    }

    @Override
    public void getMyCoinDetails(int page, RxAppCompatActivity activity, boolean isLoadMore) {
        model.getMyCoinDetails(page).compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseObserver<BaseResponse<CoinDetail>>() {
            @Override
            public void onSuccess(BaseResponse<CoinDetail> data) {
                getBaseView().onMyCoinDetails(data.getData().getDatas(), isLoadMore);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onMyCoinDetailsError(error, isLoadMore);
            }
        });
    }
}
