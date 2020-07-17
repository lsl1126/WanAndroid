package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

public class ProjectContract {
    public interface IProjectView extends IBaseView {

        void onProjectTree(List<ArticlesTree> list);

        void onProjectArticle(List<Articles> list, boolean isLoadMore);

        void onProjectTreeError(String error);

        void onProjectArticleError(String error, boolean isLoadMore);

        void onCollect(int position, boolean isCollect);

        void onCollectError(String error);
    }

    public interface IProjectPresenter {
        //项目分类
        void getProjectTree(RxFragment fragment);

        //项目文章
        void getProjectArticle(RxFragment fragment, int page, int cid, boolean isLoadMore);
    }
}
