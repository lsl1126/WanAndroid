package com.lsl.wanandroid.ui.my.myCollect.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseMvpActivity;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.ui.my.myCollect.mvp.contract.MyCollectContract;
import com.lsl.wanandroid.ui.my.myCollect.mvp.presenter.MyCollectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectActivity extends BaseMvpActivity<MyCollectContract.IMyCollectView, MyCollectPresenter> implements MyCollectContract.IMyCollectView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private MyCollectPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initView() {
        initToolBar(toolbar);
        presenter = new MyCollectPresenter();
    }


    @Override
    public void onArticle(List<Articles> articles, boolean isLoadMore) {

    }

    @Override
    public void onError(String error, boolean isLoadMore) {

    }

    @Override
    public void onCollect(int position, boolean isCollect) {

    }

    @Override
    public void onCollectError(String error) {

    }

    @Override
    protected MyCollectPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}