package com.lsl.wanandroid.ui.search.mvp.contract;

import android.content.Context;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.sql.entity.SearchHistory;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * Created by lsl on 2020/6/15/015.
 */
public class SearchContract {
    public interface ISearchView extends IBaseView {
        void onSearchSuccess(List<Articles> list, boolean isLoadMore);

        void onError(String error, boolean isLoadMore);

        void onHotKey(List<HotKey> list);

        void onHotKeyError(String error);

        void onAllHistory(List<SearchHistory> list);

        void onHistory(SearchHistory searchHistory, String text);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);

//        void onAddHistory();
//
//        void onDeleteHistory();
//
//        void onDeleteAllHistory();
    }

    public interface ISearchContentPresenter {
        void search(int page, String text, RxAppCompatActivity activity, boolean isLoadMore);

        void getHotKey(RxAppCompatActivity activity);

        void getAllHistory(Context context);

        void getHistory(Context context, String text);

        void addHistory(Context context, SearchHistory searchHistory);

        void deleteHistory(Context context, SearchHistory searchHistory);

        void deleteAll(Context context);
    }
}
