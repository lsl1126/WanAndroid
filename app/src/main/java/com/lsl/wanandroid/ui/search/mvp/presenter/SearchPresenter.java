package com.lsl.wanandroid.ui.search.mvp.presenter;

import android.content.Context;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.sql.entity.SearchHistory;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.search.mvp.contract.SearchContract;
import com.lsl.wanandroid.ui.search.mvp.model.SearchModel;
import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * Created by lsl on 2020/6/15/015.
 */
public class SearchPresenter extends BasePresenter<SearchContract.ISearchView> implements SearchContract.ISearchContentPresenter, CollectPresenter.CollectCallBack {
    private SearchModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public SearchPresenter() {
        model = new SearchModel();
    }

    @Override
    public void search(int page, String text, RxAppCompatActivity activity, boolean isLoadMore) {
        model.search(page, text).compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseObserver<BaseResponse<ArticlesInfo>>() {
            @Override
            public void onSuccess(BaseResponse<ArticlesInfo> data) {
                getBaseView().onSearchSuccess(data.getData().getDatas(), isLoadMore);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error, isLoadMore);
            }
        });
    }

    @Override
    public void getHotKey(RxAppCompatActivity activity) {
        model.getHotKey().compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseObserver<BaseResponse<List<HotKey>>>() {
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
    public void getAllHistory(Context context) {
        getBaseView().onAllHistory(SearchModel.getInstance(context).searchDao().getAll());
    }

    @Override
    public void getHistory(Context context, String text) {
        getBaseView().onHistory(SearchModel.getInstance(context).searchDao().getHistory(text), text);
    }

    @Override
    public void addHistory(Context context, SearchHistory searchHistory) {
        SearchModel.getInstance(context).searchDao().insert(searchHistory);
    }

    @Override
    public void deleteHistory(Context context, SearchHistory searchHistory) {
        SearchModel.getInstance(context).searchDao().delete(searchHistory);
    }

    @Override
    public void deleteAll(Context context) {
        SearchModel.getInstance(context).searchDao().deleteAll();
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
