package com.lsl.wanandroid.ui.my.myCoin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.CoinRankAdapter;
import com.lsl.wanandroid.base.BaseMvpActivity;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.NewestFragment;
import com.lsl.wanandroid.ui.my.myCoin.mvp.contract.CoinRankContract;
import com.lsl.wanandroid.ui.my.myCoin.mvp.presenter.CoinRankPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCoinRankActivity extends BaseMvpActivity<CoinRankContract.ICoinRankView, CoinRankPresenter> implements CoinRankContract.ICoinRankView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    private CoinRankPresenter presenter;
    private CoinRankAdapter adapter;
    private List<Coin.DatasBean> list = new ArrayList<>();
    //页码
    private int page = 1;
    //数据总数
    private int dataNum = 0;

    public static void startIntent(Context context) {
        Intent intent = new Intent(context, MyCoinRankActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_rank;
    }

    @Override
    protected void initView() {
        initToolBar(toolbar);
        presenter = new CoinRankPresenter();
        adapter = new CoinRankAdapter(R.layout.item_coin_rank, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initListener() {
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getCoinRank(page, MyCoinRankActivity.this, true);
            }
        });
        Objects.requireNonNull(multiStateView.getView(MultiStateView.ViewState.ERROR)).findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                page = 0;
                presenter.getCoinRank(page, MyCoinRankActivity.this, false);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getCoinRank(page, this, false);
    }

    @Override
    public void onCoinRank(List<Coin.DatasBean> dataBeanList, boolean isLoadMore) {
        if (dataBeanList != null) {
            if (isLoadMore) {
                list.addAll(dataBeanList);
                if (dataNum == list.size()) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    adapter.getLoadMoreModule().loadMoreComplete();
                    dataNum = list.size();
                }
            } else {
                multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                list.clear();
                list.addAll(dataBeanList);
                adapter.notifyDataSetChanged();
            }
        } else {
            if (isLoadMore) {
                adapter.getLoadMoreModule().loadMoreEnd();
            } else {
                multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
            }
        }
    }

    @Override
    public void onError(String error, boolean isLoadMore) {
        if (isLoadMore) {
            adapter.getLoadMoreModule().loadMoreFail();
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected CoinRankPresenter getPresenter() {
        return presenter;
    }
}