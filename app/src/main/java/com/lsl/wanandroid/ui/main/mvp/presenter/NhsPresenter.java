package com.lsl.wanandroid.ui.main.mvp.presenter;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.main.mvp.contract.NewestContract;
import com.lsl.wanandroid.ui.main.mvp.contract.NhsContract;
import com.lsl.wanandroid.ui.main.mvp.model.NhsModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/15
 */
public class NhsPresenter extends BasePresenter<NhsContract.INhsView> implements NhsContract.INhsPresenter {
    private NhsModel model;

    public NhsPresenter() {
        model = new NhsModel();
    }

    @Override
    public void getNhsTree(RxFragment fragment) {
        model.getNhsTree().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<List<ArticlesTree>>() {
            @Override
            public void onSuccess(List<ArticlesTree> data) {
                getBaseView().onNhsTree(data);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error);
            }
        });
    }
}
