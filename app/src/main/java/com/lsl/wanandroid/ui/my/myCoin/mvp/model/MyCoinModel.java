package com.lsl.wanandroid.ui.my.myCoin.mvp.model;

import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;

import io.reactivex.Observable;

/**
 * Create by lsl on 2020/7/19
 */
public class MyCoinModel {

    //个人积分
    public Observable<BaseResponse<Coin.DatasBean>> getMyCoin() {
        return RetrofitUtils.getApiUrl().getMyCoin();
    }

    //个人积分明细
    public Observable<BaseResponse<CoinDetail>> getMyCoinDetails(int page) {
        return RetrofitUtils.getApiUrl().getMyCoinDetails(page);
    }
}
