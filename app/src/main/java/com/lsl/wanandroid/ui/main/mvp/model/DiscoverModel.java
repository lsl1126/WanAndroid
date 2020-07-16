package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.bean.Friend;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/7/7
 */
public class DiscoverModel {
    //轮播
    public Observable<BaseResponse<List<Banners>>> getBanner() {
        return RetrofitUtils.getApiUrl().getBanner();
    }

    //搜索热词
    public Observable<BaseResponse<List<HotKey>>> getHotKey() {
        return RetrofitUtils.getApiUrl().getHotKey();
    }

    //常用网址
    public Observable<BaseResponse<List<Friend>>> getFriend() {
        return RetrofitUtils.getApiUrl().getFriend();
    }
}
