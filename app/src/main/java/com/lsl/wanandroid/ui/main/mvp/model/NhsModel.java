package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/6/15
 */
public class NhsModel {
    //体系分类
    public Observable<BaseResponse<List<ArticlesTree>>> getNhsTree() {
        return RetrofitUtils.getApiUrl().getNhsTree();
    }
}
