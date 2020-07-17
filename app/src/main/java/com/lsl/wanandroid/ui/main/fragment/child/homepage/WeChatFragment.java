package com.lsl.wanandroid.ui.main.fragment.child.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.android.material.tabs.TabLayout;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.SimpleArticleAdapter;
import com.lsl.wanandroid.base.BaseLazyMvpFragment;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.main.mvp.contract.WeChatContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.WeChatPresenter;
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
public class WeChatFragment extends BaseLazyMvpFragment<WeChatContract.IWeChatView, WeChatPresenter> implements WeChatContract.IWeChatView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    //页码
    private int page = 2;
    //数据总数
    private int dataNum = 0;
    //项目分类Id
    private int cid;

    //判断项目分类获取错误类别
    private boolean isWechatTreeError;

    private WeChatPresenter presenter;
    private SimpleArticleAdapter adapter;
    private List<Articles> list = new ArrayList<>();
    private List<ArticlesTree> articlesTreeList = new ArrayList<>();
    private LoadingDialog loadingDialog;

    public static WeChatFragment newInstance() {
        return new WeChatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
    }

    @Override
    protected void initLazyView() {
        EventBus.getDefault().register(this);
        presenter = new WeChatPresenter();
        loadingDialog = new LoadingDialog(getContext());
        adapter = new SimpleArticleAdapter(R.layout.item_article, list);
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
                presenter.getWeChatArticle(WeChatFragment.this, page, cid, true);
            }
        });
        Objects.requireNonNull(multiStateView.getView(MultiStateView.ViewState.ERROR)).findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                if (isWechatTreeError) {
                    tabLayout.removeAllTabs();
                    multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                    presenter.getWeChatTree(WeChatFragment.this);
                } else {
                    multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                    presenter.getWeChatArticle(WeChatFragment.this, page, cid, false);
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (articlesTreeList != null) {
                    cid = articlesTreeList.get(tab.getPosition()).getId();
                    refresh();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initLazyData() {
        presenter.getWeChatTree(this);
    }

    private void refresh() {
        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
        page = 1;
        dataNum = 0;
        presenter.getWeChatArticle(WeChatFragment.this, page, cid, false);
    }

    @Override
    public void onRefresh() {
        page = 1;
        dataNum = 0;
        presenter.getWeChatArticle(WeChatFragment.this, page, cid, false);
    }

    @Override
    public void onWeCharTree(List<ArticlesTree> treeList) {
        if (treeList != null && treeList.size() > 0) {
            articlesTreeList.addAll(treeList);
            for (ArticlesTree articlesTree : treeList) {
                TabLayout.Tab tab = tabLayout.newTab();
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
                tab.setCustomView(view);
                TextView tvText = view.findViewById(R.id.tv_Text);
                tvText.setText(articlesTree.getName());
                tabLayout.addTab(tab);
            }
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    @Override
    public void onWeCharTreeError(String error) {
        isWechatTreeError = true;
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWeChatArticles(List<Articles> articlesList, boolean isLoadMore) {
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
                recyclerView.scrollToPosition(0);
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
    public void onWeChatArticlesError(String error, boolean isLoadMore) {
        isWechatTreeError = false;
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
    protected WeChatPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}

