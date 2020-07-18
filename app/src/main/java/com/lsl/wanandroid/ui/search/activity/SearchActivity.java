package com.lsl.wanandroid.ui.search.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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
import com.lsl.wanandroid.base.BaseMvpActivity;
import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.dialog.ChooseDialogFragment;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.sql.entity.SearchHistory;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.search.mvp.contract.SearchContract;
import com.lsl.wanandroid.ui.search.mvp.presenter.SearchPresenter;
import com.lsl.wanandroid.ui.webView.WebActivity;
import com.lsl.wanandroid.utils.Constants;
import com.lsl.wanandroid.utils.SoftKeyboardUtils;
import com.lsl.wanandroid.wdiget.ClearEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseMvpActivity<SearchContract.ISearchView, SearchPresenter> implements SearchContract.ISearchView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Text)
    ClearEditText etText;
    @BindView(R.id.ll_All)
    LinearLayout llAll;
    @BindView(R.id.ll_HotKey)
    LinearLayout llHotKey;
    @BindView(R.id.ll_History)
    LinearLayout llHistory;
    @BindView(R.id.flowLayout_HotKey)
    TagFlowLayout flowLayoutHotKey;
    @BindView(R.id.flowLayout_History)
    TagFlowLayout flowLayoutHistory;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private SearchPresenter presenter;
    private ArticleAdapter adapter;
    private List<Articles> list = new ArrayList<>();
    private List<HotKey> hotKeyList = new ArrayList<>();
    private List<SearchHistory> historyList = new ArrayList<>();
    private int page = 0;
    private int dataNum = 0;
    private ChooseDialogFragment fragment;
    private LoadingDialog loadingDialog;

    public static void startIntent(Activity activity, View view) {
        Intent intent = new Intent(activity, SearchActivity.class);
        if (Build.VERSION.SDK_INT > 20) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "llSearch").toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initToolBar(toolbar);
        SoftKeyboardUtils.setEditTextState(etText);
        presenter = new SearchPresenter();
        loadingDialog = new LoadingDialog(this);
        adapter = new ArticleAdapter(R.layout.item_article, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
        adapter.addChildClickViewIds(R.id.image_Collect);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                WebActivity.startIntent(SearchActivity.this, list.get(position).getTitle(), list.get(position).getLink());
            }
        });
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.image_Collect) {
                    if (isLogin()) {
                        loadingDialog.show();
                        if (list.get(position).isCollect()) {
                            presenter.collect(SearchActivity.this, list.get(position).getId(), position, false);
                        } else {
                            presenter.collect(SearchActivity.this, list.get(position).getId(), position, true);
                        }
                    } else {
                        LoginActivity.startIntent(SearchActivity.this);
                    }
                }
            }
        });
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                search(true);
            }
        });
        Objects.requireNonNull(multiStateView.getView(MultiStateView.ViewState.ERROR)).findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                page = 0;
                search(false);
            }
        });
        etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    search(false);
                    return true;
                }
                return false;
            }
        });
        flowLayoutHotKey.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String text = hotKeyList.get(position).getName().trim();
                etText.setText(text);
                etText.setSelection(text.length());
                search(false);
                return true;
            }
        });
        flowLayoutHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String text = historyList.get(position).getText().trim();
                etText.setText(text);
                etText.setSelection(text.length());
                search(false);
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getHotKey(this);
        presenter.getAllHistory(this);
    }

    private void search(boolean isLoadMore) {
        String text = Objects.requireNonNull(etText.getText()).toString().trim();
        if (!TextUtils.isEmpty(text)) {
            SoftKeyboardUtils.hideSoftKeyboard(SearchActivity.this);
            if (llAll.getVisibility() == View.VISIBLE) {
                llAll.setVisibility(View.GONE);
            }
            if (llHotKey.getVisibility() == View.VISIBLE) {
                llHotKey.setVisibility(View.GONE);
            }
            if (llHistory.getVisibility() == View.VISIBLE) {
                llHistory.setVisibility(View.GONE);
            }
            if (multiStateView.getVisibility() == View.GONE) {
                multiStateView.setVisibility(View.VISIBLE);
            }
            if (!isLoadMore && !refreshLayout.isRefreshing()) {
                multiStateView.setViewState(MultiStateView.ViewState.LOADING);
            }
            presenter.getHistory(this, text);
            presenter.search(page, text, this, isLoadMore);
        } else {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        dataNum = 0;
        search(false);
    }

    @Override
    public void onSearchSuccess(List<Articles> articlesList, boolean isLoadMore) {
        if (articlesList != null && articlesList.size() > 0) {
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
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHotKey(List<HotKey> list) {
        if (list != null && list.size() > 0) {
            hotKeyList.clear();
            hotKeyList.addAll(list);
            flowLayoutHotKey.setAdapter(new TagAdapter(hotKeyList) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = new TextView(SearchActivity.this);
                    textView.setBackgroundResource(R.drawable.shape_solid_grey200_corner_20dp);
                    textView.setTextColor(Color.BLACK);
                    textView.setPadding(30, 10, 30, 10);
                    textView.setText(hotKeyList.get(position).getName());
                    return textView;
                }
            });
            llHotKey.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHotKeyError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAllHistory(List<SearchHistory> list) {
        if (list != null && list.size() > 0) {
            historyList.clear();
            historyList.addAll(list);
            flowLayoutHistory.setAdapter(new TagAdapter(historyList) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView textView = new TextView(SearchActivity.this);
                    textView.setBackgroundResource(R.drawable.shape_solid_grey200_corner_20dp);
                    textView.setTextColor(Color.BLACK);
                    textView.setPadding(30, 10, 30, 10);
                    textView.setText(historyList.get(position).getText());
                    return textView;
                }
            });
            llHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHistory(SearchHistory searchHistory, String text) {
        if (searchHistory == null) {
            presenter.addHistory(this, new SearchHistory(text));
        }
    }

    @Override
    public void onCollect(int position, boolean isCollect) {
        loadingDialog.dismiss();
        if (isCollect) {
            list.get(position).setCollect(true);
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            list.get(position).setCollect(false);
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onCollectError(String error) {
        loadingDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tv_Search, R.id.image_Delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_Search:
                search(false);
                break;
            case R.id.image_Delete:
                deleteDialog();
                break;
        }
    }

    private void deleteDialog() {
        if (fragment == null) {
            fragment = ChooseDialogFragment.newInstance("清除历史记录");
            fragment.setOnSelectClickListener("确认", "取消", new ChooseDialogFragment.OnSelectClickListener() {
                @Override
                public void onYesClick() {
                    presenter.deleteAll(SearchActivity.this);
                    llHistory.setVisibility(View.GONE);
                    fragment.dismiss();
                }

                @Override
                public void onNoClick() {
                    fragment.dismiss();
                }
            });
        }
        fragment.show(getSupportFragmentManager(), "清除历史记录");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        SoftKeyboardUtils.hideSoftKeyboard(this);
        if (Build.VERSION.SDK_INT > 20) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(Event event) {
        if (event.getMessage().equals(Constants.LOGIN_SUCCESS)) {
            multiStateView.setViewState(MultiStateView.ViewState.LOADING);
            page = 0;
            dataNum = 0;
            search(false);
        }
    }

    @Override
    protected SearchPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}