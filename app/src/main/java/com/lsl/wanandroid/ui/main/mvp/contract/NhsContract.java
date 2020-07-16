package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/15
 */
public class NhsContract {
    public interface INhsView extends IBaseView {
        void onNhsTree(List<ArticlesTree> list);

        void onError(String error);
    }

    public interface INhsPresenter {
        //体系分类
        void getNhsTree(RxFragment fragment);
    }
}
