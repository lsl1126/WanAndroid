package com.lsl.wanandroid.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseLazyMvpFragment<V extends IBaseView, P extends BasePresenter<V>> extends BaseLazyFragment {
    private P presenter = null;

    @Override
    protected void initPresenter() {
        if (presenter == null) {
            presenter = (P) getPresenter();
        }
        presenter.bindView((V)this);
    }

    protected abstract P getPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.unBindView();
        }
    }
}
