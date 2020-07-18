package com.lsl.wanandroid.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.lsl.wanandroid.sql.entity.SearchHistory;

import java.util.List;

/**
 * Created by lsl on 2020/7/18/018.
 */
@Dao
public interface SearchDao {
    //查询全部数据
    @Query("SELECT * FROM searchhistory")
    List<SearchHistory> getAll();

    //查询某一项数据
    @Query("SELECT * FROM searchhistory WHERE text=:text")
    SearchHistory getHistory(String text);

    //添加数据
    @Insert
    void insert(SearchHistory... searchHistories);

    //删除全部数据
    @Query("DELETE FROM searchhistory")
    void deleteAll();

    //删除某一项
    @Delete
    void delete(SearchHistory searchHistory);
}
