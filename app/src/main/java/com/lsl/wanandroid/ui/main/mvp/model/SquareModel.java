package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/6/14
 */
public class SquareModel {

    //广场文章
    public Observable<BaseResponse<ArticlesInfo>> getArticles(int page) {
        return RetrofitUtils.getApiUrl().getSquareArticle(page);
    }
}
