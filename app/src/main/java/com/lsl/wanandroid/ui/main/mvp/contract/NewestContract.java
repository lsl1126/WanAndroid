package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/14
 */
public class NewestContract {
    public interface INewestView extends IBaseView {
        void onArticle(List<Articles> articles, boolean isLoadMore);

        void onError(String error, boolean isLoadMore);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);
    }

    public interface INewestPresenter {
        //最新文章
        void getArticle(RxFragment fragment, int page, boolean isLoadMore);
    }
}
