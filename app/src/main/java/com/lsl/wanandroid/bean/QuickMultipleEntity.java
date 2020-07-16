package com.lsl.wanandroid.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Create by lsl on 2020/6/26
 */
public class QuickMultipleEntity implements MultiItemEntity {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private int itemType;
    private User userBean;
    private UiBean uiBean;

    public QuickMultipleEntity(int itemType, User userBean) {
        this.itemType = itemType;
        this.userBean = userBean;
    }

    public QuickMultipleEntity(int itemType, UiBean uiBean) {
        this.itemType = itemType;
        this.uiBean = uiBean;
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public UiBean getUiBean() {
        return uiBean;
    }

    public void setUiBean(UiBean uiBean) {
        this.uiBean = uiBean;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
