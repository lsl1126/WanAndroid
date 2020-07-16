package com.lsl.wanandroid.ui.main.fragment.main;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseFragment;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.HotFragment;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.NewestFragment;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.ProjectFragment;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.SquareFragment;
import com.lsl.wanandroid.ui.main.fragment.child.homepage.WeChatFragment;
import com.lsl.wanandroid.ui.search.activity.SearchActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ll_Search)
    LinearLayout llSearch;

    private String[] str = {"热门", "最新", "广场", "项目", "公众号"};

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView() {
        List<RxFragment> fragmentList = new ArrayList<>();
        fragmentList.add(HotFragment.newInstance());
        fragmentList.add(NewestFragment.newInstance());
        fragmentList.add(SquareFragment.newInstance());
        fragmentList.add(ProjectFragment.newInstance());
        fragmentList.add(WeChatFragment.newInstance());
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return str[position];
            }
        });
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.ll_Search)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.ll_Search) {
            SearchActivity.startIntent(getActivity(), llSearch);
        }
    }
}
