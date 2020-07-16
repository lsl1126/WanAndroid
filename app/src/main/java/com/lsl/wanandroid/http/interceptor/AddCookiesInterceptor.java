package com.lsl.wanandroid.http.interceptor;

import android.content.Context;

import com.lsl.wanandroid.utils.PersistenceUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lsl on 2020/7/15/015.
 */
public class AddCookiesInterceptor implements Interceptor {

    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> hashSet = (HashSet<String>) PersistenceUtils.getCookie(context);
        if (hashSet != null) {
            for (String cookie : hashSet) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
