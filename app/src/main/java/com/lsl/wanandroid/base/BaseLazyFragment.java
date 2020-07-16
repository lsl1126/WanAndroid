package com.lsl.wanandroid.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.lsl.wanandroid.utils.PersistenceUtils;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.Objects;

import butterknife.ButterKnife;

public abstract class BaseLazyFragment extends RxFragment {
    //当前Fragment的View是否已经创建
    private boolean isViewCreated;
    //当前Fragment的View是否对用户可见
    private boolean isViewToUser;
    //当前Fragment的View是否已加载数据
    private boolean isViewLoad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        isViewCreated = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewToUser = isVisibleToUser;
        lazy();
    }

    private void lazy() {
        if (!isViewCreated || !isViewToUser || isViewLoad) {
            return;
        }
        initLazyView();
        initPresenter();
        initListener();
        initLazyData();
        isViewLoad = true;
    }

    protected void initPresenter() {
    }

    protected void initListener() {
    }

    protected boolean isLogin() {
        return PersistenceUtils.getIsLogin(Objects.requireNonNull(getContext()));
    }

    protected abstract void initLazyView();

    protected abstract void initLazyData();

    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isViewToUser = false;
        isViewLoad = false;
    }
}
