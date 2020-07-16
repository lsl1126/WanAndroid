package com.lsl.wanandroid.ui.main.mvp.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.bean.Friend;
import com.lsl.wanandroid.bean.HotKey;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/7/7
 */
public class DiscoverContract {
    public interface IDiscoverView extends IBaseView {
        void onBanner(List<Banners> list);

        void onHotKey(List<HotKey> list);

        void onFriend(List<Friend> list);

        void onBannerError(String error);

        void onHotKeyError(String error);

        void onFriendError(String error);

    }

    public interface IDiscoverPresenter {
        //轮播
        void getBanner(RxFragment fragment);

        //搜索热词
        void getHotKey(RxFragment fragment);

        //常用网站
        void getFriend(RxFragment fragment);
    }
}
