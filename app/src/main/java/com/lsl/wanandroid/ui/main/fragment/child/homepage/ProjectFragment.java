package com.lsl.wanandroid.ui.main.fragment.child.homepage;

import android.annotation.SuppressLint;
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
import com.lsl.wanandroid.adapter.ArticleAdapter;
import com.lsl.wanandroid.base.BaseLazyMvpFragment;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.main.mvp.contract.ProjectContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.ProjectPresenter;
import com.lsl.wanandroid.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lsl on 2020/6/12/012.
 */
public class ProjectFragment extends BaseLazyMvpFragment<ProjectContract.IProjectView, ProjectPresenter> implements ProjectContract.IProjectView {
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
    private boolean isProjectTreeError;

    private ProjectPresenter presenter;
    private ArticleAdapter adapter;
    private List<Articles> list = new ArrayList<>();
    private List<ArticlesTree> articlesTreeList = new ArrayList<>();
    private LoadingDialog loadingDialog;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initLazyView() {
        EventBus.getDefault().register(this);
        presenter = new ProjectPresenter();
        loadingDialog = new LoadingDialog(getContext());
        adapter = new ArticleAdapter(R.layout.item_article, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.addChildClickViewIds(R.id.image_Collect);
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
                presenter.getProjectArticle(ProjectFragment.this, page, cid);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                dataNum = 0;
                presenter.getProjectArticle(ProjectFragment.this, page, cid);
            }
        });
        View view = multiStateView.getView(MultiStateView.ViewState.ERROR);
        if (view != null) {
            view.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    if (isProjectTreeError) {
                        tabLayout.removeAllTabs();
                        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                        presenter.getProjectTree(ProjectFragment.this);
                    } else {
                        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                        presenter.getProjectArticle(ProjectFragment.this, page, cid);
                    }
                }
            });
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (articlesTreeList != null) {
                    page = 1;
                    dataNum = 0;
                    list.clear();
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(0);
                    cid = articlesTreeList.get(tab.getPosition()).getId();
                    multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                    presenter.getProjectArticle(ProjectFragment.this, page, cid);
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
        presenter.getProjectTree(this);
    }


    @Override
    @SuppressLint("InflateParams")
    public void onProjectTree(List<ArticlesTree> treeList) {
        if (treeList != null && treeList.size() > 0) {
            articlesTreeList.addAll(treeList);
            tabLayout.setVisibility(View.VISIBLE);
            for (ArticlesTree articlesTree : treeList) {
                TabLayout.Tab tab = tabLayout.newTab();
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
                tab.setCustomView(view);
                TextView tvText = view.findViewById(R.id.tv_Text);
                tvText.setText(articlesTree.getName());
                tabLayout.addTab(tab);
            }
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }


    @Override
    public void onProjectArticle(List<Articles> articlesList) {
        if (articlesList != null) {
            multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
            if (refreshLayout.isRefreshing()) {
                list.clear();
                refreshLayout.setRefreshing(false);
            }
            list.addAll(articlesList);
            if (list.size() == dataNum) {
                adapter.getLoadMoreModule().loadMoreEnd();
            } else {
                adapter.getLoadMoreModule().loadMoreComplete();
                dataNum = list.size();
            }
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    @Override
    public void onProjectTreeError(String error) {
        isProjectTreeError = true;
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProjectArticleError(String error) {
        isProjectTreeError = false;
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCollect(int position, boolean isCollect) {
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
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(Event event) {
        if (event.getMessage().equals(Constants.LOGIN_SUCCESS)) {
            multiStateView.setViewState(MultiStateView.ViewState.LOADING);
            articlesTreeList.clear();
            tabLayout.removeAllTabs();
            presenter.getProjectTree(this);
        }
    }

    @Override
    protected ProjectPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}

