package com.lsl.wanandroid.ui.my.myCoin.mvp.model;

import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/7/19
 */
public class CoinRankModel {

    //积分排行
    public Observable<BaseResponse<Coin>> getCoinRank(int page) {
        return RetrofitUtils.getApiUrl().getCoinRank(page);
    }
}
