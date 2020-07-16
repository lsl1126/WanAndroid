package com.lsl.wanandroid.ui.main.mvp.presenter;

import android.content.Context;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.main.mvp.contract.NhsChildContract;
import com.lsl.wanandroid.ui.main.mvp.contract.ProjectContract;
import com.lsl.wanandroid.ui.main.mvp.model.NhsChildModel;
import com.lsl.wanandroid.ui.main.mvp.model.ProjectModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/14
 */
public class NhsChildPresenter extends BasePresenter<NhsChildContract.INhsChildView> implements NhsChildContract.INhsChildPresenter, CollectPresenter.CollectCallBack {
    private NhsChildModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public NhsChildPresenter() {
        this.model = new NhsChildModel();
    }

    @Override
    public void getNhsArticle(RxFragment fragment, int page, int cid) {
        model.getNhsArticle(page, cid).compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<ArticlesInfo>() {
            @Override
            public void onSuccess(ArticlesInfo data) {
                getBaseView().onNhsArticle(data.getDatas());
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error);
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
