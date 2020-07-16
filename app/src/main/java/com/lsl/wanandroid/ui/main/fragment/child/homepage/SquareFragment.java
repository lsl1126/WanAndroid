package com.lsl.wanandroid.ui.main.fragment.child.homepage;

import android.util.Log;
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
import com.lsl.wanandroid.adapter.SimpleArticleAdapter;
import com.lsl.wanandroid.base.BaseLazyMvpFragment;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.main.mvp.contract.SquareContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.SquarePresenter;
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
public class SquareFragment extends BaseLazyMvpFragment<SquareContract.ISquareView, SquarePresenter> implements SquareContract.ISquareView, SwipeRefreshLayout.OnRefreshListener {
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

    private SquarePresenter presenter;
    private SimpleArticleAdapter adapter;
    private List<Articles> list = new ArrayList<>();
    private LoadingDialog loadingDialog;

    public static SquareFragment newInstance() {
        return new SquareFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initLazyView() {
        EventBus.getDefault().register(this);
        presenter = new SquarePresenter();
        loadingDialog = new LoadingDialog(getContext());
        adapter = new SimpleArticleAdapter(R.layout.item_simple_article, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
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
                presenter.getArticle(SquareFragment.this, page);
            }
        });

//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                onRefresh();
//            }
//        });
        View view = multiStateView.getView(MultiStateView.ViewState.ERROR);
        if (view != null) {
            view.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                    presenter.getArticle(SquareFragment.this, page);
                }
            });
        }
    }

    @Override
    protected void initLazyData() {
        presenter.getArticle(this, page);
    }

    @Override
    public void onRefresh() {
        page = 0;
        dataNum = 0;
        presenter.getArticle(SquareFragment.this, page);
    }

    @Override
    public void onArticle(List<Articles> articlesList) {
        if (articlesList != null) {
            if (refreshLayout.isRefreshing()) {
                list.clear();
                list.addAll(articlesList);
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            } else {
                list.addAll(articlesList);
                if (dataNum == list.size()) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    adapter.getLoadMoreModule().loadMoreComplete();
                    dataNum = list.size();
                }
                if (multiStateView.getViewState() != MultiStateView.ViewState.CONTENT) {
                    multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                }
            }
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    @Override
    public void onError(String error) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        adapter.getLoadMoreModule().loadMoreFail();
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
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    onRefresh();
                }
            });
        }
    }

    @Override
    protected SquarePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
