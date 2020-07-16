package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

public class HotContract {
    public interface IHotView extends IBaseView {
        void onAllArticle(List<Articles> list);

        void onLoadArticle(List<Articles> list);

        void onLoadError(String error);

        void onAllError(String error);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);
    }

    public interface IHotPresenter {
        //置顶+首页文章
        void getAllArticle(RxFragment fragment);

        //加载更多首页文章
        void getArticle(RxFragment fragment, int page);
    }
}
