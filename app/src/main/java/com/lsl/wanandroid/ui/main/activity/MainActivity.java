package com.lsl.wanandroid.ui.main.activity;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseActivity;
import com.lsl.wanandroid.ui.main.fragment.main.DiscoverFragment;
import com.lsl.wanandroid.ui.main.fragment.main.HomePageFragment;
import com.lsl.wanandroid.ui.main.fragment.main.MyFragment;
import com.lsl.wanandroid.ui.main.fragment.main.NavigationFragment;
import com.lsl.wanandroid.ui.main.fragment.main.NhsFragment;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_nav)
    BottomNavigationView navigationView;

    private HomePageFragment homePageFragment;
    private NhsFragment nhsFragment;
    private DiscoverFragment discoverFragment;
    private NavigationFragment navigationFragment;
    private MyFragment myFragment;

    private Fragment isFragment;
    private long firstClickTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance();
        }
        isFragment = homePageFragment;
        ft.replace(R.id.fl, homePageFragment).commit();
    }

    @Override
    protected void initListener() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home_page:
                        if (homePageFragment == null) {
                            homePageFragment = HomePageFragment.newInstance();
                        }
                        switchContent(isFragment, homePageFragment);
                        break;
                    case R.id.navigation_nhs:
                        if (nhsFragment == null) {
                            nhsFragment = NhsFragment.newInstance();
                        }
                        switchContent(isFragment, nhsFragment);
                        break;

                    case R.id.navigation_discover:
                        if (discoverFragment == null) {
                            discoverFragment = DiscoverFragment.newInstance();
                        }
                        switchContent(isFragment, discoverFragment);
                        break;
                    case R.id.navigation_navigation:
                        if (navigationFragment == null) {
                            navigationFragment = NavigationFragment.newInstance();
                        }
                        switchContent(isFragment, navigationFragment);
                        break;
                    case R.id.navigation_my:
                        if (myFragment == null) {
                            myFragment = MyFragment.newInstance();
                        }
                        switchContent(isFragment, myFragment);
                        break;
                }
                return true;
            }
        });
    }

    public void switchContent(Fragment from, Fragment to) {
        if (isFragment != to) {
            isFragment = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                ft.hide(from).add(R.id.fl, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                ft.hide(from).show(to).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstClickTime >= 2000) {
            Toast.makeText(this, "再按一次返回键，退出程序", Toast.LENGTH_SHORT).show();
            firstClickTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
