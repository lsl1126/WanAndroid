package com.lsl.wanandroid.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseMvpActivity;
import com.lsl.wanandroid.bean.Event;
import com.lsl.wanandroid.bean.User;
import com.lsl.wanandroid.dialog.LoadingDialog;
import com.lsl.wanandroid.ui.login.contract.LoginContract;
import com.lsl.wanandroid.ui.login.presenter.LoginPresenter;
import com.lsl.wanandroid.utils.Constants;
import com.lsl.wanandroid.utils.PersistenceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginContract.ILoginView, LoginPresenter> implements LoginContract.ILoginView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_UserName)
    EditText etUserName;
    @BindView(R.id.et_PassWord)
    EditText etPassWord;

    public static final int LOGIN_SUCCESS = 1001;


    private LoginPresenter presenter;
    private LoadingDialog loadingDialog;

    public static void startIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        initToolBar(toolbar);
        presenter = new LoginPresenter();
        loadingDialog = new LoadingDialog(this, "正在登录中...");
    }

    @OnClick(R.id.btn_Login)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_Login) {
            if (isVerify()) {
                loadingDialog.show();
                presenter.login(etUserName.getText().toString().trim(), etPassWord.getText().toString().trim(), this);
            }
        }
    }

    private boolean isVerify() {
        if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etPassWord.getText().toString().trim())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onLogin(User user) {
        loadingDialog.dismiss();
        PersistenceUtils.setIsLogin(this, true);
        PersistenceUtils.saveUserInfo(this, String.valueOf(new Gson().toJson(user)));
        EventBus.getDefault().post(new Event(Constants.LOGIN_SUCCESS));
        finish();
    }

    @Override
    public void onError(String error) {
        loadingDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected LoginPresenter getPresenter() {
        return presenter;
    }
}
