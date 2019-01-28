package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends Activity {

    private User user;

    private PhotoAdapter photoAdapter = null;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.photosLoading_progressBar);
        refreshLayout = findViewById(R.id.swiperefresh); // TODO rename

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPhotosFromHttp();
            }
        });

        user = new User(getSharedPreferences(User.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE));

        setupRecyclerView();
        getPhotosFromHttp();
    }


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    public void logOut(View view){

        user.logOut();
        startLoginActivity();
    }

    private void startLoginActivity(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void setupRecyclerView(){

        if(photoAdapter == null)
            photoAdapter = new PhotoAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(photoAdapter);
    }

    private void getPhotosFromHttp(){

        DataRetrievalKt.getPhotosService().photoObject().enqueue(new Callback<PhotoObject>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PhotoObject> call, Response<PhotoObject> response) {

                PhotoObject photoObject = response.body();

                if(photoObject != null){
                    photoAdapter.updateItems(photoObject.getArray());
                }

                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PhotoObject> call, Throwable t) {

                Log.e("photosService", t.getLocalizedMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
