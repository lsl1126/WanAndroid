package com.lsl.wanandroid.wdiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lsl.wanandroid.R;
import com.lsl.wanandroid.utils.DisplayUtils;

/**
 * Create by lsl on 2020/6/26
 */
public class ClearEditText extends androidx.appcompat.widget.AppCompatEditText implements View.OnLayoutChangeListener, TextWatcher, View.OnFocusChangeListener {
    private Drawable drawable;
    private boolean isHasFocus;

    public ClearEditText(Context context) {
        super(context);
        initView();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        drawable = getResources().getDrawable(R.drawable.ic_delete_gray);
        addOnLayoutChangeListener(this);
        addTextChangedListener(this);
        setOnFocusChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        drawable.setBounds(0, 0, (int) (getMeasuredHeight() / 2.5), (int) (getMeasuredHeight() / 2.5));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isHasFocus) {
            if (s.length() > 0) {
                setDrawable(true);
            } else {
                setDrawable(false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        isHasFocus = hasFocus;
        if (getText() != null) {
            if (isHasFocus && getText().length() > 0) {
                setDrawable(true);
            } else {
                setDrawable(false);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (drawable != null) {
                int start = getWidth() - getTotalPaddingRight() + getPaddingRight(); // 起始位置
                int end = getWidth(); // 结束位置
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setDrawable(boolean visible) {
        if (visible) {
            setCompoundDrawablePadding(DisplayUtils.dip2px(getContext(), 8));
            setCompoundDrawables(null, null, drawable, null);
        } else {
            setCompoundDrawablePadding(DisplayUtils.dip2px(getContext(), 0));
            setCompoundDrawables(null, null, null, null);
        }
    }
}
