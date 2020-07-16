package com.lsl.wanandroid.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<V extends IBaseView, P extends BasePresenter<V>> extends BaseActivity {
    private P presenter = null;

    protected void initPresenter() {
        if (presenter == null) {
            presenter = getPresenter();
        }
        presenter.bindView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unBindView();
            presenter = null;
        }
    }

    protected abstract P getPresenter();
}
