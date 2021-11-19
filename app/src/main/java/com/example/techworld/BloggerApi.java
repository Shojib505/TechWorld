package com.example.techworld;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class BloggerApi {

    public static final String key  = "AIzaSyDjHiurUbQZSwWcKxHpD_X_tK-xkEFJpW4";
    public static final String uri = "https://www.googleapis.com/blogger/v3/blogs/5162531475882400976/posts/";

    public static GetPostService getPostService = null;

    public static GetPostService service(){
        if (getPostService == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(uri)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            getPostService = retrofit.create(GetPostService.class);
        }
        return getPostService;
    }

    interface GetPostService{
        @GET
        Call<Postlist> getPostList(@Url String url);
    }





}
