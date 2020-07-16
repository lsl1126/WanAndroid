package com.lsl.wanandroid.ui.search.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;

import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseMvpActivity;
import com.lsl.wanandroid.ui.search.mvp.contract.SearchContract;
import com.lsl.wanandroid.ui.search.mvp.presenter.SearchPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lsl on 2020/6/15/015.
 */
public class SearchActivity extends BaseMvpActivity<SearchContract.ISearchView, SearchPresenter> implements SearchContract.ISearchView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SearchPresenter presenter;

    public static void startIntent(Activity activity, View view) {
        Intent intent = new Intent(activity, SearchActivity.class);
        if (Build.VERSION.SDK_INT > 20) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "Search").toBundle());
        } else {
            activity.startActivity(intent);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        initToolBar(toolbar);
        presenter = new SearchPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT > 20) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    protected SearchPresenter getPresenter() {
        return presenter;
    }
}
