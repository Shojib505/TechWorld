package com.example.techworld;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAndroidActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerViewPost;
    Boolean isScrolling =false;
    int currentItem,scrolledOutItem,totalItem;
    LinearLayoutManager manager;
    AndroidAdapter adapter;
    List<Item> items = new ArrayList<>();
    String token = " ";
    SpinKitView progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_android);
        set_toolbar();
        progressBar = findViewById(R.id.spin_kit);


        recyclerViewPost = findViewById(R.id.recyclerView);
        adapter = new AndroidAdapter(this,items);
        manager = new LinearLayoutManager(this);
        recyclerViewPost.setLayoutManager(manager);
        recyclerViewPost.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);


        recyclerViewPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItem = manager.getItemCount();
                currentItem = manager.getChildCount();
                scrolledOutItem= manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItem+scrolledOutItem == totalItem)){
                    isScrolling = false;
                    getPostAndroid();

                }

            }
        });

        getPostAndroid();


    }private void set_toolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    void getPostAndroid(){

        String url = BloggerApi.uri+ "search?q=label:Android"+"?key="+ BloggerApi.key;
        if (token!=" "){
            url = url +"&pageToken="+token;
        }
        if (token == null){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Call<Postlist> postlistCall = BloggerApi.service().getPostList(url);
        postlistCall.enqueue(new Callback<Postlist>() {

            @Override
            public void onResponse(Call<Postlist> call, Response<Postlist> response) {
                Postlist list = response.body();
                token = list.getNextPageToken();
                items.addAll(list.getItems());
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Loading Success full",Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Postlist> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Sorry App Is Crushed Call Shojib",Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.VISIBLE);
            }
        }
        );

    }



}
