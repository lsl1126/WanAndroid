package com.lsl.wanandroid.http;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lsl on 2020/6/11/011.
 */
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T httpResult) {
        if (httpResult != null) {
            onSuccess(httpResult);
        } else {
            onFailure(null, "数据获取失败");
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
