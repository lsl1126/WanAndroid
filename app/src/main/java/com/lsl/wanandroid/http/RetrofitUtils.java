package com.lsl.wanandroid.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.lsl.wanandroid.http.interceptor.AddCookiesInterceptor;
import com.lsl.wanandroid.http.interceptor.SaveCookiesInterceptor;
import com.lsl.wanandroid.utils.Constants;
import com.lsl.wanandroid.utils.MyApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lsl on 2020/6/11/011.
 */
public class RetrofitUtils {

    private static final int DEFAULT_CONNECT_TIME = 10;
    private static final int DEFAULT_WRITE_TIME = 30;
    private static final int DEFAULT_READ_TIME = 30;

    private static ApiService apiUrl;

    public static ApiService getApiUrl() {
        if (apiUrl == null) {
            synchronized (RetrofitUtils.class) {
                if (apiUrl == null) {
                    apiUrl = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return apiUrl;
    }

    private ApiService getRetrofit() {
        return initRetrofit().create(ApiService.class);
    }


    private Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient())
                .build();
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.i("onResponse", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                //添加log日志打印
                .addInterceptor(httpLoggingInterceptor)
                //首次获取的cookie
                .addInterceptor(new SaveCookiesInterceptor(MyApplication.getContext()))
                //保存的cookie
                .addInterceptor(new AddCookiesInterceptor(MyApplication.getContext()))
                .retryOnConnectionFailure(true)
                .build();
    }
}
