package com.lsl.wanandroid.ui.collect;

import android.content.Context;

import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RxHelper;
import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

/**
 * Create by lsl on 2020/7/11
 */
public class CollectPresenter {
    private Context context;
    private CollectModel model;
    private CollectCallBack collectCallBack;

    public CollectPresenter(Context context, CollectCallBack collectCallBack) {
        this.model = new CollectModel();
        this.context = context;
        this.collectCallBack = collectCallBack;
    }

    public void collect(int id) {
        model.collect(id).compose(RxHelper.observableIO2Main(context)).subscribe(new BaseObserver<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> data) {
                collectCallBack.onCollectSuccess(true);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                collectCallBack.onCollectError(error);
            }
        });
    }

    public void unCollect(int id) {
        model.unCollect(id).compose(RxHelper.observableIO2Main(context)).subscribe(new BaseObserver<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> data) {
                collectCallBack.onCollectSuccess(false);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                collectCallBack.onCollectError(error);
            }
        });
    }

    public interface CollectCallBack {
        void onCollectSuccess(boolean isCollect);

        void onCollectError(String error);
    }
}
