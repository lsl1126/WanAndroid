package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lsl on 2020/6/12/012.
 */
public class HotModel {
    //置顶文章
    public Observable<BaseResponse<List<Articles>>> getTopArticle() {
        return RetrofitUtils.getApiUrl().getTopArticle();
    }

    //首页文章
    public Observable<BaseResponse<ArticlesInfo>> getArticle(int page) {
        return RetrofitUtils.getApiUrl().getHomePageArticle(page);
    }
}
