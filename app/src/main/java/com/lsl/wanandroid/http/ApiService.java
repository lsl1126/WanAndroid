package com.lsl.wanandroid.http;


import com.lsl.wanandroid.bean.Articles;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.bean.Banners;
import com.lsl.wanandroid.bean.Friend;
import com.lsl.wanandroid.bean.HotKey;
import com.lsl.wanandroid.bean.Navigation;
import com.lsl.wanandroid.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //登录
    @POST("user/login")
    Observable<BaseResponse<User>> login(@Query("username") String userName, @Query("password") String password);

    //置顶文章
    @GET("article/top/json")
    Observable<BaseResponse<List<Articles>>> getTopArticle();

    //首页文章列表
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticlesInfo>> getHomePageArticle(@Path("page") int page);

    //最新文章列表
    @GET("article/listproject/{page}/json")
    Observable<BaseResponse<ArticlesInfo>> getNewArticle(@Path("page") int page);

    //广场文章列表
    @GET("user_article/list/{page}/json")
    Observable<BaseResponse<ArticlesInfo>> getSquareArticle(@Path("page") int page);

    //项目分类列表
    @GET("project/tree/json")
    Observable<BaseResponse<List<ArticlesTree>>> getProjectTree();

    //项目文章列表
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ArticlesInfo>> getProjectArticle(@Path("page") int page, @Query("cid") int cid);

    //公众号分类列表
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<ArticlesTree>>> getWeChatTree();

    //公众号文章列表
    @GET("wxarticle/list/{cid}/{page}/json")
    Observable<BaseResponse<ArticlesInfo>> getWeChatArticle(@Path("page") int page, @Path("cid") int cid);

    //体系分类列表
    @GET("tree/json")
    Observable<BaseResponse<List<ArticlesTree>>> getNhsTree();

    //体系文章
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticlesInfo>> getNhsArticle(@Path("page") int page, @Query("cid") int cid);

    //轮播
    @GET("banner/json")
    Observable<BaseResponse<List<Banners>>> getBanner();

    //搜索热词
    @GET("hotkey/json")
    Observable<BaseResponse<List<HotKey>>> getHotKey();

    //常用网址
    @GET("friend/json")
    Observable<BaseResponse<List<Friend>>> getFriend();

    //常用网址
    @GET("navi/json")
    Observable<BaseResponse<List<Navigation>>> getNavigation();

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<String>> collect(@Path("id") int id);

    //取消收藏站内文章
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse<String>> unCollect(@Path("id") int id);
}
