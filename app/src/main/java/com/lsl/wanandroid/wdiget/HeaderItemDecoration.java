package com.lsl.wanandroid.wdiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsl.wanandroid.R;
import com.lsl.wanandroid.bean.Navigation;
import com.lsl.wanandroid.utils.DisplayUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by lsl on 2020/7/8/008.
 */
public class HeaderItemDecoration extends RecyclerView.ItemDecoration {
    private int dp10;
    private int dp16;
    private Rect rect;
    private Paint textPaint;
    private Paint itemHeaderPaint;
    private List<Navigation> list;

    public HeaderItemDecoration(Context context, List<Navigation> list) {
        this.list = list;
        this.rect = new Rect();
        this.textPaint = new Paint();
        this.textPaint.setColor(context.getResources().getColor(R.color.color_gray1000));
        this.textPaint.setTextSize(DisplayUtils.sp2px(context, 16));
        this.itemHeaderPaint = new Paint();
        this.itemHeaderPaint.setColor(Color.WHITE);
        this.textPaint.setAntiAlias(true);
        this.itemHeaderPaint.setAntiAlias(true);
        this.dp10 = DisplayUtils.dip2px(context, 10);
        this.dp16 = DisplayUtils.dip2px(context, 16);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager != null) {
            int position = layoutManager.findFirstVisibleItemPosition();
            String title = list.get(position).getName();
            textPaint.getTextBounds(title, 0, title.length(), rect);
            int textHeight = rect.height();
            int itemHeaderHeight = textHeight + dp10 * 2;
            c.drawRect(0, 0, parent.getRight(), itemHeaderHeight, itemHeaderPaint);
            c.drawText(title, dp16, (float) itemHeaderHeight / 2 + (float) textHeight / 2, textPaint);







            //    View view = layoutManager.findViewByPosition(position);
//            if (list.get(position + 1).getName() != null && !title.equals(list.get(position + 1).getName())) {
//                if (view != null) {
//                    int bottom = Math.min(itemHeaderHeight, view.getBottom());
//                    c.drawRect(0, view.getTop() - itemHeaderHeight, parent.getWidth(), bottom + dp10, itemHeaderPaint);
//                    c.drawText(title, dp16, (float) itemHeaderHeight / 2 + (float) textHeight / 2 - (float) (itemHeaderHeight - bottom - dp10) / 2, textPaint);
//                }
//            } else {
//                c.drawRect(0, 0, parent.getRight(), itemHeaderHeight, itemHeaderPaint);
//                c.drawText(title, dp16, (float) itemHeaderHeight / 2 + (float) textHeight / 2, textPaint);
//            }
        }


//
//
//
//            View view = layoutManager.findViewByPosition(position);


//
//
//            String title = list.get(position).getName();
//            boolean flag = false;
//            if (list.get(position + 1).getName() != null && !title.equals(list.get(position + 1).getName())) {
//                if (view.getHeight() + view.getTop() < 40) {
//                    //与restore()对应，表示下面translate平移坐标系只对绘制当前标题栏生效
//                    c.save();
//                    flag = true;
//                    //translate使发生碰撞时，两个标题栏紧贴，制造出挤开的效果（dy<0,表示绘制偏下）
//                    c.translate(0, view.getHeight() + view.getTop() - dp35);
//                }
//            }
//            paint.setColor(Color.WHITE);
//            c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(),
//                    parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + dp35, paint);
//            paint.setColor(Color.BLACK);
//            paint.setTextSize(50);
//            c.drawText(title, view.getPaddingLeft(),
//                    parent.getPaddingTop() + dp35 - (dp35 - 40) / 2, paint);
//
//            if (flag) {
//                c.restore();
//            }
    }


//        if (list != null && list.size() > 0) {
//            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
//            if (layoutManager != null) {
//                int position = layoutManager.findFirstVisibleItemPosition();
//                String title = list.get(position).getName();
//                View view = layoutManager.findViewByPosition(position);
//                paint.setColor(Color.WHITE);
//                c.drawRect(0, 0, parent.getRight(), dp20 + 40, paint);
//                paint.setColor(Color.BLACK);
//                paint.setTextSize(50);
//                if (view != null) {
//                    c.drawText(title, view.getPaddingLeft(), (float) dp20 / 2 + 40, paint);
//                }
//            }
//        }
}
