package com.lsl.wanandroid.ui.my.myCollect.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/7/22
 */
public class MyCollectContract {

    public interface IMyCollectView extends IBaseView {
        void onArticle(List<Articles> articles, boolean isLoadMore);

        void onError(String error, boolean isLoadMore);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);
    }

    public interface IMyCollectPresenter {
        //我的收藏
        void getMyCollect(RxAppCompatActivity activity, int page, boolean isLoadMore);
    }
}
