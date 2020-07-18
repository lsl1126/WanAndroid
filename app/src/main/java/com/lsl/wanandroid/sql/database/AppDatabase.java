package com.lsl.wanandroid.sql.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lsl.wanandroid.sql.dao.SearchDao;
import com.lsl.wanandroid.sql.entity.SearchHistory;

/**
 * Created by lsl on 2020/7/18/018.
 */
@Database(entities = {SearchHistory.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SearchDao searchDao();
}
