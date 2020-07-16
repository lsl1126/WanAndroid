package com.lsl.wanandroid.ui.login.model;


import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.bean.User;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

public class LoginModel {
    //登录
    public Observable<BaseResponse<User>> login(String userName, String passWord) {
        return RetrofitUtils.getApiUrl().login(userName, passWord);
    }
}
