package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Navigation;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Created by lsl on 2020/7/8/008.
 */
public class NavigationContract {
    public interface INavigationView extends IBaseView {
        void onNavigation(List<Navigation> list);

        void onError(String error);
    }

    public interface INavigationPresenter {
        //导航
        void getNavigation(RxFragment fragment);
    }
}
