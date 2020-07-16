package com.lsl.wanandroid.ui.main.mvp.presenter;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.bean.Friend;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.main.mvp.contract.DiscoverContract;
import com.lsl.wanandroid.ui.main.mvp.model.DiscoverModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/7/7
 */
public class DiscoverPresenter extends BasePresenter<DiscoverContract.IDiscoverView> implements DiscoverContract.IDiscoverPresenter {

    private DiscoverModel model;

    public DiscoverPresenter() {
        this.model = new DiscoverModel();
    }

    @Override
    public void getBanner(RxFragment fragment) {
        model.getBanner().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseObserver<BaseResponse<List<Banners>>>() {
            @Override
            public void onSuccess(BaseResponse<List<Banners>> data) {
                getBaseView().onBanner(data.getData());
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onBannerError(error);
            }
        });
    }

    @Override
    public void getHotKey(RxFragment fragment) {
        model.getHotKey().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseObserver<BaseResponse<List<HotKey>>>() {
            @Override
            public void onSuccess(BaseResponse<List<HotKey>> data) {
                getBaseView().onHotKey(data.getData());
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onHotKeyError(error);
            }
        });
    }

    @Override
    public void getFriend(RxFragment fragment) {
        model.getFriend().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseObserver<BaseResponse<List<Friend>>>() {
            @Override
            public void onSuccess(BaseResponse<List<Friend>> data) {
                getBaseView().onFriend(data.getData());
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onFriendError(error);
            }
        });
    }
}
