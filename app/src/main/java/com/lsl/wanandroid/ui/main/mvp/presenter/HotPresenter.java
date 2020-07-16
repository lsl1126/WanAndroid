package com.lsl.wanandroid.ui.main.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxExceptionUtils;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.main.mvp.contract.HotContract;
import com.lsl.wanandroid.ui.main.mvp.model.HotModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lsl on 2020/6/12/012.
 */
public class HotPresenter extends BasePresenter<HotContract.IHotView> implements HotContract.IHotPresenter, CollectPresenter.CollectCallBack {
    private HotModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public HotPresenter() {
        this.model = new HotModel();
    }

    @Override
    public void getAllArticle(RxFragment fragment) {
        Observable.zip(model.getTopArticle(), model.getArticle(0), (listBaseResponse, listBaseResponse2) -> {
            List<Articles> articlesList = listBaseResponse.getData();
            ArticlesInfo articlesInfo = listBaseResponse2.getData();
            return new ArticleEntity(articlesList, articlesInfo);
        }).compose(RxHelper.observableIO2Main(fragment))
                .subscribe(new BaseObserver<ArticleEntity>() {
                    @Override
                    public void onSuccess(ArticleEntity data) {
                        List<Articles> list = new ArrayList<>();
                        for (Articles articles : data.articlesResponse) {
                            articles.setTop(true);
                            list.add(articles);
                        }
                        list.addAll(data.articlesInfo.getDatas());
                        getBaseView().onAllArticle(list);
                    }

                    @Override
                    public void onFailure(Throwable e, String error) {
                        getBaseView().onAllError(RxExceptionUtils.exceptionHandler(e));
                    }
                });
    }

    @Override
    public void getArticle(RxFragment fragment, int page) {
        model.getArticle(page).compose(RxHelper.observableIO2Main(fragment))
                .subscribe(new BaseResObserver<ArticlesInfo>() {
                    @Override
                    public void onSuccess(ArticlesInfo data) {
                        getBaseView().onLoadArticle(data.getDatas());
                    }

                    @Override
                    public void onFailure(Throwable e, String error) {
                        getBaseView().onLoadError(error);
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

    /**
     * 合并置顶，首页文章列表的实体
     */
    private static class ArticleEntity {

        private List<Articles> articlesResponse;

        private ArticlesInfo articlesInfo;

        public ArticleEntity(List<Articles> articlesResponse, ArticlesInfo articlesInfo) {
            this.articlesResponse = articlesResponse;
            this.articlesInfo = articlesInfo;
        }

        public List<Articles> getArticlesResponse() {
            return articlesResponse;
        }

        public void setArticlesResponse(List<Articles> articlesResponse) {
            this.articlesResponse = articlesResponse;
        }

        public ArticlesInfo getArticlesInfo() {
            return articlesInfo;
        }

        public void setArticlesInfo(ArticlesInfo articlesInfo) {
            this.articlesInfo = articlesInfo;
        }
    }
}
