package com.lsl.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lsl.wanandroid.bean.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lsl on 2020/7/9/009.
 */
public class PersistenceUtils {


    public static void saveCookie(Context context, HashSet<String> hashSet) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putStringSet("Cookie", hashSet);
        edit.apply();
    }


    public static Set<String> getCookie(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet("Cookie", null);
    }

    //保存用户登录状态
    public static void setIsLogin(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("IsLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("IsLogin", b);
        edit.apply();
    }

    //获取用户登录状态
    public static boolean getIsLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("IsLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsLogin", false);
    }

    //保存用户信息至本地
    public static void saveUserInfo(Context context, String userInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserInfo", userInfo);
        editor.apply();
    }

    //获取本地保存的用户信息
    public static User getUserInfo(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        String userInfo = sharedPreferences.getString("UserInfo", "");
        JsonParser parser = new JsonParser();
        JsonElement e = null;
        if (userInfo != null) {
            e = parser.parse(userInfo);
        }
        return new Gson().fromJson(e, User.class);
    }

    //清空本地保存的用户信息
    public static void clearUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
