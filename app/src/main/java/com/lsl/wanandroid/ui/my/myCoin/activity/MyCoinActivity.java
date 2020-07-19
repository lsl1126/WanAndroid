package com.lsl.wanandroid.ui.my.myCoin.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.CoinDetailsAdapter;
import com.lsl.wanandroid.base.BaseMvpActivity;
import com.lsl.wanandroid.bean.Coin;
import com.lsl.wanandroid.bean.CoinDetail;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.NewestFragment;
import com.lsl.wanandroid.ui.my.myCoin.mvp.contract.MyCoinContract;
import com.lsl.wanandroid.ui.my.myCoin.mvp.presenter.MyCoinPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCoinActivity extends BaseMvpActivity<MyCoinContract.IMyCoinView, MyCoinPresenter> implements MyCoinContract.IMyCoinView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_Rank)
    AppCompatImageView imageRank;
    @BindView(R.id.tv_Coin)
    TextView tvCoin;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyCoinPresenter presenter;
    private CoinDetailsAdapter adapter;
    private List<CoinDetail.DatasBean> list = new ArrayList<>();
    private ValueAnimator animator;

    //页码
    private int page = 0;
    //数据总数
    private int dataNum = 0;

    public static void startIntent(Context context) {
        Intent intent = new Intent(context, MyCoinActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coin;
    }

    @Override
    @SuppressLint("InflateParams")
    protected void initView() {
        initToolBar(toolbar);
        presenter = new MyCoinPresenter();
        adapter = new CoinDetailsAdapter(R.layout.item_coin_details, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_my_coin, null, false));
    }

    @Override
    protected void initListener() {
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getMyCoinDetails(page, MyCoinActivity.this, true);
            }
        });
        Objects.requireNonNull(multiStateView.getView(MultiStateView.ViewState.ERROR)).findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiStateView.setViewState(MultiStateView.ViewState.LOADING);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getMyCoin(this);
        presenter.getMyCoinDetails(page, this, false);
    }

    @Override
    public void onMyCoin(Coin.DatasBean coin) {
//        animator = ValueAnimator.ofFloat(0, coin.getCoinCount());
//        animator.setDuration(1000);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                String text = animation.getAnimatedValue().toString();
//                tvCoin.setText(text.substring(0, text.indexOf(".")));
//            }
//        });
//        animator.start();
    }

    @Override
    public void onMyCoinError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMyCoinDetails(List<CoinDetail.DatasBean> beanList, boolean isLoadMore) {
        if (beanList != null) {
            if (isLoadMore) {
                list.addAll(beanList);
                if (dataNum == list.size()) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    adapter.getLoadMoreModule().loadMoreComplete();
                    dataNum = list.size();
                }
            } else {
                multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                list.clear();
                list.addAll(beanList);
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
    public void onMyCoinDetailsError(String error, boolean isLoadMore) {
        if (isLoadMore) {
            adapter.getLoadMoreModule().loadMoreFail();
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override
    protected MyCoinPresenter getPresenter() {
        return presenter;
    }
}