package com.lsl.wanandroid.ui.main.fragment.main;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseMvpFragment;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.ui.main.fragment.child.nhs.NhsChildFragment;
import com.lsl.wanandroid.ui.main.mvp.contract.NhsContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.NhsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NhsFragment extends BaseMvpFragment<NhsContract.INhsView, NhsPresenter> implements NhsContract.INhsView {
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private NhsPresenter presenter;

    public static NhsFragment newInstance() {
        return new NhsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nhs;
    }

    @Override
    protected void initView() {
        presenter = new NhsPresenter();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        presenter.getNhsTree(this);
    }

    @Override
    public void onNhsTree(List<ArticlesTree> list) {
        if (list != null) {
            viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
                @NonNull
                @Override
                public Fragment getItem(int position) {
                    return NhsChildFragment.newInstance((ArrayList<ArticlesTree.ChildrenBean>) list.get(position).getChildren());
                }

                @Override
                public int getCount() {
                    return list.size();
                }

                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {
                    return list.get(position).getName();
                }
            });
            viewPager.setOffscreenPageLimit(5);
            tabLayout.setupWithViewPager(viewPager);
            multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
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
    protected NhsPresenter getPresenter() {
        return presenter;
    }
}
