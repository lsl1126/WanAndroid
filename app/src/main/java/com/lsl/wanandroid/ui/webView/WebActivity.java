package com.lsl.wanandroid.ui.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.kennyc.view.MultiStateView;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebActivity extends BaseActivity {

    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_Title)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.image_Collect)
    AppCompatImageView imageCollect;

    public static void startIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("Url", url);
        intent.putExtra("Title", title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        initToolBar(toolbar);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                multiStateView.setViewState(MultiStateView.ViewState.ERROR);
            }
        });
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("Title");
        String url = getIntent().getStringExtra("Url");
        tvTitle.setText(title);
        webView.loadUrl(url);
    }

    @OnClick({R.id.image_Share, R.id.image_Collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_Share:
                Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_Collect:
                Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}