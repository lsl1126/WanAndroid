package com.lsl.wanandroid.ui.collect;

import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/7/11
 */
public class CollectModel {
    public Observable<BaseResponse<String>> collect(int id) {
        return RetrofitUtils.getApiUrl().collect(id);
    }

    public Observable<BaseResponse<String>> unCollect(int id) {
        return RetrofitUtils.getApiUrl().unCollect(id);
    }
}
