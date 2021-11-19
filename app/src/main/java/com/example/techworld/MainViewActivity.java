package com.example.techworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerViewPost;
    Boolean isScrolling =false;
    int currentItem,scrolledOutItem,totalItem;
    LinearLayoutManager manager;
    PostAdapter adapter;
    List<Item> items = new ArrayList<>();
    String token = "";
    SpinKitView progressBar;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set_toolbar();
        navigationView = findViewById(R.id.navigation_view);
        progressBar = findViewById(R.id.spin_kit);


        recyclerViewPost = findViewById(R.id.recyclerView);
        adapter = new PostAdapter(this,items);
        manager = new LinearLayoutManager(this);
        recyclerViewPost.setLayoutManager(manager);
        recyclerViewPost.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.android:
                        Intent intent = new Intent  (MainViewActivity.this,ListAndroidActivity.class);
                        intent.putExtra("url",BloggerApi.uri+"search?q=label:Android"+"?key="+BloggerApi.key);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"android",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.hacking:
                        Toast.makeText(getApplicationContext(),"Hacking",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tech_news:
                        Toast.makeText(getApplicationContext(),"Tech News",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.guide:
                        Toast.makeText(getApplicationContext(),"Guide Line",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.seting:
                        Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.rate_app:
                        Toast.makeText(getApplicationContext(),"Rate",Toast.LENGTH_LONG).show();
                        break;
                }


                return false;
            }
        });
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
                    getPost();

                }

            }
        });

        getPost();


    }private void set_toolbar() {
        drawerLayout = findViewById(R.id.drawer_id);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
     }

     void getPost(){

        String url = BloggerApi.uri+ "?key="+ BloggerApi.key;
        if (token!=""){
            url = url +"&pageToken="+token;
        }
        if (token ==null){
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
        });
     }
}
