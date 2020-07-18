package com.lsl.wanandroid.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lsl.wanandroid.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lsl on 2020/7/18/018.
 */
public class ChooseDialogFragment extends DialogFragment {
    @BindView(R.id.tv_Message)
    TextView tvMessage;
    @BindView(R.id.tv_No)
    TextView tvNo;
    @BindView(R.id.tv_Yes)
    TextView tvYes;
    private OnSelectClickListener onSelectClickListener;
    private String message, yes, no;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public void setOnSelectClickListener(String yes, String no, OnSelectClickListener onSelectClickListener) {
        if (!TextUtils.isEmpty(yes)) {
            this.yes = yes;
        }
        if (!TextUtils.isEmpty(no)) {
            this.no = no;
        }
        this.onSelectClickListener = onSelectClickListener;
    }

    public static ChooseDialogFragment newInstance(String message) {
        ChooseDialogFragment fragment = new ChooseDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Message", message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString("Message");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_dialog, parent, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tvMessage.setText(message);
        tvYes.setText(yes);
        tvNo.setText(no);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            DisplayMetrics d = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics();
            params.gravity = Gravity.CENTER;
            params.width = (int) (d.widthPixels * 0.8);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @OnClick({R.id.tv_No, R.id.tv_Yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_No:
                onSelectClickListener.onNoClick();
                break;
            case R.id.tv_Yes:
                onSelectClickListener.onYesClick();
                break;
        }
    }

    public interface OnSelectClickListener {
        void onYesClick();

        void onNoClick();
    }
}
