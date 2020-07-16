package com.lsl.wanandroid.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.QuickMultipleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Create by lsl on 2020/6/26
 */
public class UiAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

    public UiAdapter(List<QuickMultipleEntity> list) {
        super(list);
        addItemType(QuickMultipleEntity.TYPE_LEVEL_0, R.layout.item_user);
        addItemType(QuickMultipleEntity.TYPE_LEVEL_1, R.layout.item_right_image_right_title_left_arrow);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, QuickMultipleEntity item) {
        switch (helper.getItemViewType()) {
            case QuickMultipleEntity.TYPE_LEVEL_0:
                if (item.getUserBean() != null) {
                    helper.setGone(R.id.image_Arrow, true);
                    helper.setText(R.id.tv_Name, item.getUserBean().getPublicName());
                } else {
                    helper.setGone(R.id.image_Arrow, false);
                    helper.setText(R.id.tv_Name, getContext().getResources().getString(R.string.text_login_register));
                }
                break;
            case QuickMultipleEntity.TYPE_LEVEL_1:
                helper.setText(R.id.tv_Title, item.getUiBean().getTitle());
                helper.setImageResource(R.id.image, item.getUiBean().getImageRescure());
                break;
            default:
                break;
        }
    }
}
