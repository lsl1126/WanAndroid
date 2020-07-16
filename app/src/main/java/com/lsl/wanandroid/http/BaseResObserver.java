package com.lsl.wanandroid.http;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lsl on 2020/6/11/011.
 */
public abstract class BaseResObserver<T> implements Observer<BaseResponse<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> httpResult) {
        if (httpResult.getErrorCode() == 0) {
            if (httpResult.getData() != null) {
                onSuccess(httpResult.getData());
            } else {
                onFailure(null, "获取数据失败");
            }
        } else {
            onFailure(null, httpResult.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e, RxExceptionUtils.exceptionHandler(e));
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(Throwable e, String error);
}
