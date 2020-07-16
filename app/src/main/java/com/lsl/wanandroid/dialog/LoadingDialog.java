package com.lsl.wanandroid.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lsl.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lsl on 2020/7/9/009.
 */
public class LoadingDialog extends AlertDialog {

    @BindView(R.id.tv_Text)
    TextView tvText;
    private String text = "正在加载中...";

    public LoadingDialog(Context context) {
        super(context, R.style.Dialog_style);
    }

    public LoadingDialog(Context context, String text) {
        super(context, R.style.Dialog_style);
        if (!TextUtils.isEmpty(text)) {
            this.text = text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        ButterKnife.bind(this);
        tvText.setText(text);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getWindow();
        if (window != null) {
            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (metrics.widthPixels * 0.35);
            params.height = (int) (metrics.widthPixels * 0.35);
            window.setAttributes(params);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
