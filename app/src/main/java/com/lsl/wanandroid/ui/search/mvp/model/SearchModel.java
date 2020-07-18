package com.lsl.wanandroid.ui.search.mvp.model;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RetrofitUtils;
import com.lsl.wanandroid.sql.database.AppDatabase;
import com.lsl.wanandroid.sql.entity.SearchHistory;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lsl on 2020/6/15/015.
 */
public class SearchModel {

    //搜索关键词
    public Observable<BaseResponse<ArticlesInfo>> search(int page, String text) {
        return RetrofitUtils.getApiUrl().search(page, text);
    }

    //热门搜索
    public Observable<BaseResponse<List<HotKey>>> getHotKey() {
        return RetrofitUtils.getApiUrl().getHotKey();
    }

    private static AppDatabase database;

    public static AppDatabase getInstance(Context context) {
        if (database == null) {
            synchronized (SearchModel.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context,
                            AppDatabase.class, "searchHistory.db")
                            .allowMainThreadQueries()
                            .addMigrations(new Migration(1, 2) {
                                @Override
                                public void migrate(@NonNull SupportSQLiteDatabase database) {
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return database;
    }

    //查询
    public static void getAllHistory(Context context) {
        getInstance(context).searchDao().getAll();
    }

    //查询
    public static void getSearchHistory(Context context, String text) {
        getInstance(context).searchDao().getHistory(text);
    }

    //添加
    public static void add(Context context, SearchHistory searchHistory) {
        getInstance(context).searchDao().insert(searchHistory);
    }

    //删除
    public static void delete(Context context, SearchHistory searchHistory) {
        getInstance(context).searchDao().delete(searchHistory);
    }

    //删除全部
    public static void deleteAll(Context context) {
        getInstance(context).searchDao().deleteAll();
    }
}
