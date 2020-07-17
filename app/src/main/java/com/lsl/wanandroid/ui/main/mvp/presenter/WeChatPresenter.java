package com.lsl.wanandroid.ui.main.mvp.presenter;

import android.content.Context;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.main.mvp.contract.WeChatContract;
import com.lsl.wanandroid.ui.main.mvp.model.WeChatModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/15
 */
public class WeChatPresenter extends BasePresenter<WeChatContract.IWeChatView> implements WeChatContract.IWeChatPresenter, CollectPresenter.CollectCallBack {
    private WeChatModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public WeChatPresenter() {
        this.model = new WeChatModel();
    }

    @Override
    public void getWeChatTree(RxFragment fragment) {
        model.getWeChatTree().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<List<ArticlesTree>>() {
            @Override
            public void onSuccess(List<ArticlesTree> data) {
                getBaseView().onWeCharTree(data);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onWeCharTreeError(error);
            }
        });
    }

    @Override
    public void getWeChatArticle(RxFragment fragment, int page, int cid, boolean isLoadMore) {
        model.getWeChatArticle(page, cid).compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<ArticlesInfo>() {
            @Override
            public void onSuccess(ArticlesInfo data) {
                getBaseView().onWeChatArticles(data.getDatas(), isLoadMore);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onWeChatArticlesError(error, isLoadMore);
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
