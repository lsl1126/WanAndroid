package com.lsl.wanandroid.ui.my.myCollect.mvp.model;

import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/7/22
 */
public class MyCollectModel {
    //获取我的收藏
    public Observable<BaseResponse<ArticlesInfo>> getMyCollect(int page) {
        return RetrofitUtils.getApiUrl().getMyCollect(page);
    }
}
