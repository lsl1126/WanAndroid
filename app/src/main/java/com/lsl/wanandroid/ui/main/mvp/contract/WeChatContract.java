package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/15
 */
public class WeChatContract {
    public interface IWeChatView extends IBaseView {
        void onWeCharTree(List<ArticlesTree> list);

        void onWeChatArticles(List<Articles> list);

        void onWeCharTreeError(String error);

        void onWeChatArticlesError(String error);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);
    }

    public interface IWeChatPresenter {
        //公众号分类
        void getWeChatTree(RxFragment fragment);

        //公众号文章
        void getWeChatArticle(RxFragment fragment, int page, int cid);
    }
}
