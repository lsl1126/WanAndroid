package com.lsl.wanandroid.ui.main.mvp.model;

import com.lsl.wanandroid.bean.Navigation;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by lsl on 2020/7/8/008.
 */
public class NavigationModel {
    //导航
    public Observable<BaseResponse<List<Navigation>>> getNavigation() {
        return RetrofitUtils.getApiUrl().getNavigation();
    }
}
