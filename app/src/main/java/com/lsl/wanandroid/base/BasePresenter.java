package com.lsl.wanandroid.base;

import android.util.Log;

public class BasePresenter<V extends IBaseView> {
    private V view = null;

    public void bindView(V view) {
        this.view = view;
    }

    public void unBindView() {
        this.view = null;
    }

    public V getBaseView() {
        return view;
    }

}
