package com.lsl.wanandroid.ui.main.mvp.presenter;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.Navigation;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.main.mvp.contract.NavigationContract;
import com.lsl.wanandroid.ui.main.mvp.model.NavigationModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Created by lsl on 2020/7/8/008.
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.INavigationView> implements NavigationContract.INavigationPresenter {
    private NavigationModel model;

    public NavigationPresenter() {
        this.model = new NavigationModel();
    }

    @Override
    public void getNavigation(RxFragment fragment) {
        model.getNavigation().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<List<Navigation>>() {
            @Override
            public void onSuccess(List<Navigation> data) {
                getBaseView().onNavigation(data);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error);
            }
        });
    }
}
