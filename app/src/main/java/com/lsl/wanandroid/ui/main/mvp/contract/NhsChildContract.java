package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

public class NhsChildContract {
    public interface INhsChildView extends IBaseView {
        void onNhsArticle(List<Articles> list);

        void onError(String error);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);

    }

    public interface INhsChildPresenter {
        //体系文章
        void getNhsArticle(RxFragment fragment, int page, int cid);
    }
}
