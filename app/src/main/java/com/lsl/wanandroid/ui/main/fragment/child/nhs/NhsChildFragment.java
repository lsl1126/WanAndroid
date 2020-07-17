package com.lsl.wanandroid.ui.main.fragment.child.nhs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.lsl.wanandroid.adapter.ArticleAdapter;
import com.lsl.wanandroid.base.BaseLazyMvpFragment;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.main.mvp.contract.NhsChildContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.NhsChildPresenter;
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
 * Create by lsl on 2020/6/15
 */
public class NhsChildFragment extends BaseLazyMvpFragment<NhsChildContract.INhsChildView, NhsChildPresenter> implements NhsChildContract.INhsChildView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;


    //页码
    private int page = 1;
    //数据总数
    private int dataNum = 0;
    //项目分类Id
    private int cid;

    private ArticleAdapter adapter;
    private NhsChildPresenter presenter;
    private List<Articles> list = new ArrayList<>();
    private List<ArticlesTree.ChildrenBean> childrenBeanList;
    private LoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nhs_child;
    }

    public static NhsChildFragment newInstance(ArrayList<ArticlesTree.ChildrenBean> childrenBeanList) {
        NhsChildFragment fragment = new NhsChildFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ChildrenBean", childrenBeanList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childrenBeanList = getArguments().getParcelableArrayList("ChildrenBean");
        }
    }

    @Override
    protected void initLazyView() {
        EventBus.getDefault().register(this);
        presenter = new NhsChildPresenter();
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
                presenter.getNhsArticle(NhsChildFragment.this, page, cid, true);
            }
        });
        Objects.requireNonNull(multiStateView.getView(MultiStateView.ViewState.ERROR)).findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (childrenBeanList != null) {
                    cid = childrenBeanList.get(tab.getPosition()).getId();
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
        if (childrenBeanList != null && childrenBeanList.size() > 0) {
            for (int i = 0; i < childrenBeanList.size(); i++) {
                TabLayout.Tab tab = tabLayout.newTab();
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
                tab.setCustomView(view);
                TextView tvText = view.findViewById(R.id.tv_Text);
                tvText.setText(childrenBeanList.get(i).getName());
                tabLayout.addTab(tab);
                tabLayout.setVisibility(View.VISIBLE);
            }
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    private void refresh() {
        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
        page = 0;
        dataNum = 0;
        presenter.getNhsArticle(NhsChildFragment.this, page, cid, false);
    }

    @Override
    public void onRefresh() {
        page = 0;
        dataNum = 0;
        presenter.getNhsArticle(NhsChildFragment.this, page, cid, false);
    }

    @Override
    public void onNhsArticle(List<Articles> articlesList, boolean isLoadMore) {
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
    protected NhsChildPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


}
