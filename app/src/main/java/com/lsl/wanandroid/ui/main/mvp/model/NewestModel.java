package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/6/14
 */
public class NewestModel {

    //获取最新文章列表
    public Observable<BaseResponse<ArticlesInfo>> getArticles(int page) {
        return RetrofitUtils.getApiUrl().getNewArticle(page);
    }
}
