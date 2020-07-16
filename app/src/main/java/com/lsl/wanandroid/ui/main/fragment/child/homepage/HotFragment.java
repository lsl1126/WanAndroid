package com.lsl.wanandroid.ui.main.fragment.child.homepage;

import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.lsl.wanandroid.base.BaseMvpFragment;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.bean.User;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.main.mvp.contract.HotContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.HotPresenter;
import com.lsl.wanandroid.utils.Constants;
import com.lsl.wanandroid.utils.PersistenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lsl on 2020/6/12/012.
 */
public class HotFragment extends BaseMvpFragment<HotContract.IHotView, HotPresenter> implements HotContract.IHotView {
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //页码
    private int page = 1;
    //数据总数
    private int dataNum = 0;

    private ArticleAdapter adapter;
    private HotPresenter presenter;
    private List<Articles> list = new ArrayList<>();
    private LoadingDialog loadingDialog;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        presenter = new HotPresenter();
        loadingDialog = new LoadingDialog(getContext());
        adapter = new ArticleAdapter(R.layout.item_article, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getArticle(HotFragment.this, page);
            }
        });
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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getAllArticle(HotFragment.this);
            }
        });
        View view = multiStateView.getView(MultiStateView.ViewState.ERROR);
        if (view != null) {
            view.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                    presenter.getAllArticle(HotFragment.this);
                }
            });
        }
    }


    @Override
    protected void initData() {
        presenter.getAllArticle(this);
    }

    @Override
    public void onAllArticle(List<Articles> articlesList) {
        if (refreshLayout.isRefreshing()) {
            list.clear();
            refreshLayout.setRefreshing(false);
        }
        if (list != null) {
            multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
            list.addAll(articlesList);
            adapter.notifyDataSetChanged();
            dataNum = list.size();
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    @Override
    public void onAllError(String error) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadArticle(List<Articles> articlesList) {
        if (list != null) {
            list.addAll(articlesList);
            if (list.size() == dataNum) {
                adapter.getLoadMoreModule().loadMoreEnd();
            } else {
                adapter.getLoadMoreModule().loadMoreComplete();
                dataNum = list.size();
            }
        }
    }

    @Override
    public void onLoadError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
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
            multiStateView.setViewState(MultiStateView.ViewState.LOADING);
            list.clear();
            presenter.getAllArticle(this);
        }
    }

    @Override
    protected HotPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
