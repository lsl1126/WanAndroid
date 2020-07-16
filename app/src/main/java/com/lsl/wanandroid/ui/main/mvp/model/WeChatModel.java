package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;


/**
 * Create by lsl on 2020/6/15
 */
public class WeChatModel {
    //公众号分类
    public Observable<BaseResponse<List<ArticlesTree>>> getWeChatTree() {
        return RetrofitUtils.getApiUrl().getWeChatTree();
    }

    //公众号文章
    public Observable<BaseResponse<ArticlesInfo>> getWeChatArticle(int page, int cid) {
        return RetrofitUtils.getApiUrl().getWeChatArticle(page, cid);
    }
}
