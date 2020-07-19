package com.lsl.wanandroid.ui.main.fragment.main;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.adapter.UiAdapter;
import com.lsl.wanandroid.base.BaseFragment;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.bean.QuickMultipleEntity;
import com.lsl.wanandroid.bean.UiBean;
import com.lsl.wanandroid.bean.User;
import com.lsl.wanandroid.ui.login.activity.LoginActivity;
import com.lsl.wanandroid.ui.my.myCoin.activity.MyCoinActivity;
import com.lsl.wanandroid.utils.Constants;
import com.lsl.wanandroid.utils.DisplayUtils;
import com.lsl.wanandroid.utils.PersistenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private UiAdapter adapter;
    private List<QuickMultipleEntity> list = new ArrayList<>();

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        if (getContext() == null)
            return;
        EventBus.getDefault().register(this);
        int dp1 = DisplayUtils.dip2px(getContext(), 1);
        int dp10 = DisplayUtils.dip2px(getContext(), 10);
        adapter = new UiAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                if (itemPosition == 0) {
                    outRect.set(0, dp10, 0, dp10);
                } else if (itemPosition == 4) {
                    outRect.set(0, 0, 0, dp10);
                } else {
                    outRect.set(0, 0, 0, dp1);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (isLogin()) {
                    switch (position) {
                        case 1:
                            MyCoinActivity.startIntent(getContext());
                            break;
                        case 2:
                            Toast.makeText(getContext(), "我的分享", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(getContext(), "我的收藏", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(getContext(), "浏览历史", Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
                            Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    LoginActivity.startIntent(getContext());
                }
            }
        });
    }

    @Override
    protected void initData() {
        list.add(new QuickMultipleEntity(QuickMultipleEntity.TYPE_LEVEL_0, PersistenceUtils.getUserInfo(getContext())));
        list.add(new QuickMultipleEntity(QuickMultipleEntity.TYPE_LEVEL_1, new UiBean(R.drawable.icon_my_integrate, "我的积分", true)));
        list.add(new QuickMultipleEntity(QuickMultipleEntity.TYPE_LEVEL_1, new UiBean(R.drawable.icon_my_share, "我的分享", true)));
        list.add(new QuickMultipleEntity(QuickMultipleEntity.TYPE_LEVEL_1, new UiBean(R.drawable.icon_my_collect, "我的收藏", true)));
        list.add(new QuickMultipleEntity(QuickMultipleEntity.TYPE_LEVEL_1, new UiBean(R.drawable.icon_my_browse, "浏览历史", true)));
        list.add(new QuickMultipleEntity(QuickMultipleEntity.TYPE_LEVEL_1, new UiBean(R.drawable.icon_my_setting, "设置", true)));
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(Event event) {
        if (event.getMessage().equals(Constants.LOGIN_SUCCESS)) {
            User user = PersistenceUtils.getUserInfo(getContext());
            if (user != null) {
                list.get(0).setUserBean(user);
                adapter.notifyItemChanged(0);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
