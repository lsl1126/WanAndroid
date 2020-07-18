package com.lsl.wanandroid.ui.main.fragment.main;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.HotKeyAdapter;
import com.lsl.wanandroid.adapter.ImageAdapter;
import com.lsl.wanandroid.base.BaseMvpFragment;
import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.bean.Friend;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.ui.main.mvp.contract.DiscoverContract;
import com.lsl.wanandroid.ui.main.mvp.presenter.DiscoverPresenter;
import com.lsl.wanandroid.ui.webView.WebActivity;
import com.lsl.wanandroid.utils.DisplayUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DiscoverFragment extends BaseMvpFragment<DiscoverContract.IDiscoverView, DiscoverPresenter> implements DiscoverContract.IDiscoverView {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    private int dp8;
    private int dp16;
    private int dp5;
    private int dp12;
    private DiscoverPresenter presenter;
    private ImageAdapter imageAdapter;
    private HotKeyAdapter hotKeyAdapter;
    private List<Banners> bannersList = new ArrayList<>();
    private List<HotKey> hotKeyList = new ArrayList<>();
    private List<Friend> friendList = new ArrayList<>();


    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        if (getContext() == null)
            return;
        dp8 = DisplayUtils.dip2px(getContext(), 4);
        dp16 = DisplayUtils.dip2px(getContext(), 16);
        dp5 = DisplayUtils.dip2px(getContext(), 5);
        dp12 = DisplayUtils.dip2px(getContext(), 12);
        presenter = new DiscoverPresenter();
        imageAdapter = new ImageAdapter(bannersList);
        hotKeyAdapter = new HotKeyAdapter(R.layout.item_hotkey, hotKeyList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                if (getContext() != null) {
                    switch (itemPosition % 3) {
                        case 0:
                            outRect.set(dp16, dp8, dp8, dp8);
                            break;
                        case 1:
                            outRect.set(dp8, dp8, dp8, dp8);
                            break;
                        case 2:
                            outRect.set(dp8, dp8, dp16, dp8);
                            break;
                    }
                }
            }
        });
        recyclerView.setAdapter(hotKeyAdapter);
    }

    @Override
    protected void initListener() {
        imageAdapter.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                WebActivity.startIntent(getContext(), bannersList.get(position).getTitle(), bannersList.get(position).getUrl());
            }
        });
        hotKeyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                WebActivity.startIntent(getContext(),friendList.get(position).getName(),friendList.get(position).getLink());
                return true;
            }
        });
        View view = multiStateView.getView(MultiStateView.ViewState.ERROR);
        if (view != null) {
            view.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                    presenter.getBanner(DiscoverFragment.this);
                    presenter.getHotKey(DiscoverFragment.this);
                    presenter.getFriend(DiscoverFragment.this);
                }
            });
        }
    }

    @Override
    protected void initData() {
        presenter.getBanner(this);
        presenter.getHotKey(this);
        presenter.getFriend(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBanner(List<Banners> list) {
        if (list != null && list.size() > 0) {
            if (multiStateView.getViewState() != MultiStateView.ViewState.CONTENT) {
                multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
            }
            bannersList.addAll(list);
            banner.addBannerLifecycleObserver(this)
                    .setAdapter(imageAdapter)
                    .setPageTransformer(new ZoomOutPageTransformer())
                    .setIndicator(new CircleIndicator(getContext()))
                    .start();
        } else {
            if (multiStateView.getViewState() != MultiStateView.ViewState.EMPTY) {
                multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
            }
        }
    }

    @Override
    public void onHotKey(List<HotKey> list) {
        if (list != null && list.size() > 0) {
            if (multiStateView.getViewState() != MultiStateView.ViewState.CONTENT) {
                multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
            }
            hotKeyList.clear();
            hotKeyList.addAll(list);
            hotKeyAdapter.notifyDataSetChanged();
        } else {
            if (multiStateView.getViewState() != MultiStateView.ViewState.EMPTY) {
                multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onFriend(List<Friend> list) {
        if (list != null && list.size() > 0) {
            if (getContext() != null) {
                if (multiStateView.getViewState() != MultiStateView.ViewState.CONTENT) {
                    multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                }
                friendList.clear();
                friendList.addAll(list);
                flowLayout.removeAllViews();
                flowLayout.setAdapter(new TagAdapter(friendList) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = new TextView(getContext());
                        textView.setBackgroundResource(R.drawable.select_tab);
                        textView.setPadding(dp12, dp5, dp12, dp5);
                        textView.setText(list.get(position).getName());
                        return textView;
                    }
                });
            }
        } else {
            if (multiStateView.getViewState() != MultiStateView.ViewState.EMPTY) {
                multiStateView.setViewState(MultiStateView.ViewState.EMPTY);
            }
        }
    }

    @Override
    public void onBannerError(String error) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHotKeyError(String error) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFriendError(String error) {
        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected DiscoverPresenter getPresenter() {
        return presenter;
    }
}
