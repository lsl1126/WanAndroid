package com.lsl.wanandroid.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Create by lsl on 2020/7/7
 */
public class GlideUtils {
    public static void with(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void with(View view, String url, ImageView imageView) {
        Glide.with(view).load(url).into(imageView);
    }
}
