package com.lsl.wanandroid.bean;

/**
 * Create by lsl on 2020/6/26
 */
public class UiBean {


    private String ImageUrl;
    private int ImageRescure;
    private String Title;
    private boolean isShowArrow;

    public UiBean(int imageRescure, String title, boolean isShowArrow) {
        ImageRescure = imageRescure;
        Title = title;
        this.isShowArrow = isShowArrow;
    }

    public UiBean(String imageUrl, String title, boolean isShowArrow) {
        ImageUrl = imageUrl;
        Title = title;
        this.isShowArrow = isShowArrow;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getImageRescure() {
        return ImageRescure;
    }

    public void setImageRescure(int imageRescure) {
        ImageRescure = imageRescure;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isShowArrow() {
        return isShowArrow;
    }

    public void setShowArrow(boolean showArrow) {
        isShowArrow = showArrow;
    }
}
