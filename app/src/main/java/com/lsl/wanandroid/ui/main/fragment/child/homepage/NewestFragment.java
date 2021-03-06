package com.lsl.wanandroid.ui.main.fragment.child.homepage;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.ArticleAdapter;
import com.lsl.wanandroid.base.BaseLazyMvpFragment;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.main.mvp.contract.NewestContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.NewestPresenter;
import com.lsl.wanandroid.ui.webView.WebActivity;
import com.lsl.wanandroid.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Created by lsl on 2020/6/12/012.
 */
public class NewestFragment extends BaseLazyMvpFragment<NewestContract.INewestView, NewestPresenter> implements NewestContract.INewestView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //页码
    private int page = 0;
    //数据总数
    private int dataNum = 0;

    private NewestPresenter presenter;
    private ArticleAdapter adapter;
    private List<Articles> list = new ArrayList<>();
    private LoadingDialog loadingDialog;

    public static NewestFragment newInstance() {
        return new NewestFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newest;
    }

    @Override
    protected void initLazyView() {
        EventBus.getDefault().register(this);
        presenter = new NewestPresenter();
        loadingDialog = new LoadingDialog(getContext());
        adapter = new ArticleAdapter(R.layout.item_article, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
        adapter.addChildClickViewIds(R.id.image_Collect);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                WebActivity.startIntent(getContext(), list.get(position).getTitle(), list.get(position).getLink());
            }
        });
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.image_Collect) {
                    if (isLogin()) {
                        loadingDialog.show();
                        if (list.get(position).isCollect()) {
                            presenter.collect(getContext(), list.get(position).getId(), position, false);
                        } else {
                            presenter.collect(getContext(), list.get(position).getId(), position, true);
                        }
                    } else {
                        LoginActivity.startIntent(getContext());
                    }
                }
            }
        });
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getArticle(NewestFragment.this, page, true);
            }
        });
        Objects.requireNonNull(multiStateView.getView(MultiStateView.ViewState.ERROR)).findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                page = 0;
                list.clear();
                presenter.getArticle(NewestFragment.this, page, false);
            }
        });
    }

    @Override
    public void initLazyData() {
        presenter.getArticle(this, page, false);
    }

    private void refresh() {
        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
        page = 0;
        dataNum = 0;
        presenter.getArticle(NewestFragment.this, page, false);
    }

    @Override
    public void onRefresh() {
        page = 0;
        dataNum = 0;
        presenter.getArticle(NewestFragment.this, page, false);
    }

    @Override
    public void onArticle(List<Articles> articlesList, boolean isLoadMore) {
        if (articlesList != null) {
            if (isLoadMore) {
                list.addAll(articlesList);
                if (dataNum == list.size()) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    adapter.getLoadMoreModule().loadMoreComplete();
                    dataNum = list.size();
                }
            } else {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                } else {
                    multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                }
                list.clear();
                list.addAll(articlesList);
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
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            } else {
                multiStateView.setViewState(MultiStateView.ViewState.ERROR);
            }
        }
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCollect(int position, boolean isCollect) {
        loadingDialog.dismiss();
        if (isCollect) {
            list.get(position).setCollect(true);
            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            list.get(position).setCollect(false);
            Toast.makeText(getContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onCollectError(String error) {
        loadingDialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(Event event) {
        if (event.getMessage().equals(Constants.LOGIN_SUCCESS)) {
            refresh();
        }
    }

    @Override
    protected NewestPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
