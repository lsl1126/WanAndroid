package com.lsl.wanandroid.ui.main.fragment.main;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.NavigationAdapter;
import com.lsl.wanandroid.base.BaseMvpFragment;
import com.lsl.wanandroid.bean.Navigation;
import com.lsl.wanandroid.ui.main.mvp.contract.NavigationContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.NavigationPresenter;
import com.lsl.wanandroid.ui.webView.WebActivity;
import com.lsl.wanandroid.wdiget.HeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NavigationFragment extends BaseMvpFragment<NavigationContract.INavigationView, NavigationPresenter> implements NavigationContract.INavigationView {

    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NavigationPresenter presenter;
    private NavigationAdapter adapter;
    private List<Navigation> list = new ArrayList<>();

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_natigation;
    }

    @Override
    protected void initView() {
        presenter = new NavigationPresenter();
        adapter = new NavigationAdapter(R.layout.item_navigation, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HeaderItemDecoration(getContext(), list));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        presenter.getNavigation(this);
    }

    @Override
    protected void initListener() {
        adapter.setOnFlowItemClickListener(new NavigationAdapter.OnFlowItemClickListener() {
            @Override
            public void onItemClick(int position1, int position2) {
                WebActivity.startIntent(getContext(), list.get(position1).getName(), list.get(position1).getArticles().get(position2).getLink());
            }
        });
    }

    @Override
    public void onNavigation(List<Navigation> navigationList) {
        if (navigationList != null && navigationList.size() > 0) {
            multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
            list.addAll(navigationList);
            adapter.notifyDataSetChanged();
        } else {
            multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    @Override
    public void onError(String error) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected NavigationPresenter getPresenter() {
        return presenter;
    }
}
