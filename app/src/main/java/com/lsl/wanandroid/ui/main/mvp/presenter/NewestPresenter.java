package com.lsl.wanandroid.ui.main.mvp.presenter;

import android.content.Context;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.main.mvp.contract.NewestContract;
import com.lsl.wanandroid.ui.main.mvp.model.NewestModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

/**
 * Create by lsl on 2020/6/14
 */
public class NewestPresenter extends BasePresenter<NewestContract.INewestView> implements NewestContract.INewestPresenter, CollectPresenter.CollectCallBack {
    private NewestModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public NewestPresenter() {
        this.model = new NewestModel();
    }

    @Override
    public void getArticle(RxFragment fragment, int page, boolean isLoadMore) {
        model.getArticles(page).compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<ArticlesInfo>() {
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
