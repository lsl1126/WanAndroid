package com.lsl.wanandroid.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.lsl.wanandroid.R;
import com.lsl.wanandroid.utils.PersistenceUtils;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initPresenter();
        initListener();
        initData();
    }


    protected void initToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_black);
        }
    }


    protected boolean isLogin() {
        return PersistenceUtils.getIsLogin(this);
    }

    protected void initPresenter() {

    }

    protected void initListener() {

    }

    protected void initData() {
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

}
