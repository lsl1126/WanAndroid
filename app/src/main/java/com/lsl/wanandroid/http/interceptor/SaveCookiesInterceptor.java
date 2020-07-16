package com.lsl.wanandroid.http.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.lsl.wanandroid.utils.Constants;
import com.lsl.wanandroid.utils.PersistenceUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by lsl on 2020/7/15/015.
 */
public class SaveCookiesInterceptor implements Interceptor {
    private Context context;

    public SaveCookiesInterceptor(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String url = chain.request().url().toString();
        if (!TextUtils.isEmpty(url) && url.contains(Constants.URL_LOGIN) || url.contains(Constants.URL_REGISTER)) {
            if (!response.headers("Set-Cookie").isEmpty()) {
                HashSet<String> hashSet = new HashSet<>(response.headers("Set-Cookie"));
                PersistenceUtils.saveCookie(context, hashSet);
            }
        }
        return response;
    }
}
