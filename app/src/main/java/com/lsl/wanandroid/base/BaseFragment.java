package com.lsl.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.lsl.wanandroid.utils.PersistenceUtils;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.Objects;

import butterknife.ButterKnife;

public abstract class BaseFragment extends RxFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initView();
        initPresenter();
        initListener();
        initData();
        return view;
    }

    protected boolean isLogin() {
        return PersistenceUtils.getIsLogin(Objects.requireNonNull(getContext()));
    }

    protected void initPresenter() {
    }

    protected void initListener() {
    }

    protected void initData() {
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

}
