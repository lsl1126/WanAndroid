package com.lsl.wanandroid.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.utils.GlideUtils;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Create by lsl on 2020/7/7
 */
public class ImageAdapter extends BannerAdapter<Banners, ImageAdapter.BannerViewHolder> {
    public ImageAdapter(List<Banners> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, Banners data, int position, int size) {
        GlideUtils.with(holder.itemView, data.getImagePath(), holder.imageView);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }
}
