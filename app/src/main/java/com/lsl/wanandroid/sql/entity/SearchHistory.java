package com.lsl.wanandroid.sql.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by lsl on 2020/7/18/018.
 */
@Entity
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String Text;

    public SearchHistory() {
    }

    public SearchHistory(String text) {
        Text = text;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
