package com.lsl.wanandroid.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.HotFragment;
import com.lsl.wanandroid.utils.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lsl on 2020/6/12/012.
 */
public abstract class BaseMvpFragment<V extends IBaseView, P extends BasePresenter<V>> extends BaseFragment {
    private P presenter = null;

    @Override
    protected void initPresenter() {
        if (presenter == null) {
            presenter = getPresenter();
        }
        presenter.bindView((V) this);
    }

    protected abstract P getPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.unBindView();
            presenter = null;
        }
    }
}
