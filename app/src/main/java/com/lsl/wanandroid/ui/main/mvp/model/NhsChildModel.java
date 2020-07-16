package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/6/14
 */
public class NhsChildModel {
    //体系文章
    public Observable<BaseResponse<ArticlesInfo>> getNhsArticle(int page, int cid) {
        return RetrofitUtils.getApiUrl().getNhsArticle(page, cid);
    }
}
