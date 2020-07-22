package com.lsl.wanandroid.ui.my.myCollect.mvp.presenter;

import android.content.Context;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.main.mvp.model.NewestModel;
import com.lsl.wanandroid.ui.my.myCollect.mvp.contract.MyCollectContract;
import com.lsl.wanandroid.ui.my.myCollect.mvp.model.MyCollectModel;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

/**
 * Create by lsl on 2020/7/22
 */
public class MyCollectPresenter extends BasePresenter<MyCollectContract.IMyCollectView> implements MyCollectContract.IMyCollectPresenter, CollectPresenter.CollectCallBack {
    private MyCollectModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public MyCollectPresenter() {
        this.model = new MyCollectModel();
    }

    @Override
    public void getMyCollect(RxAppCompatActivity activity, int page, boolean isLoadMore) {
        model.getMyCollect(page).compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseResObserver<ArticlesInfo>() {
            @Override
            public void onSuccess(ArticlesInfo data) {
                getBaseView().onArticle(data.getDatas(), isLoadMore);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error, isLoadMore);
            }
        });
    }

    public void collect(Context context, int id, int position, boolean isCollect) {
        mPosition = position;
        if (collectPresenter == null) {
            collectPresenter = new CollectPresenter(context, this);
        }
        if (isCollect) {
            collectPresenter.collect(id);
        } else {
            collectPresenter.unCollect(id);
        }
    }

    @Override
    public void onCollectSuccess(boolean isCollect) {
        getBaseView().onCollect(mPosition, isCollect);
    }

    @Override
    public void onCollectError(String error) {
        getBaseView().onCollectError(error);
    }

}
