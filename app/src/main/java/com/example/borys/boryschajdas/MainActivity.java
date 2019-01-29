package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    private User user;

    private PhotoAdapter photoAdapter = null;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mainLayout;

    private DataRetrieval dataRetrieval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.photosLoading_progressBar);
        mainLayout = findViewById(R.id.mainLayout);

        user = new User(getSharedPreferences(User.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE));

        dataRetrieval = new DataRetrieval(new OnCustomEventListener() {
            @Override
            public void onEvent(List<Photo> photos) {
                photoAdapter.updateItems(photos);
                progressBar.setVisibility(View.GONE);
                mainLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                mainLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), R.string.connection_error_message, Toast.LENGTH_LONG).show();
            }
        });

        setupRecyclerView();
        dataRetrieval.getPhotosFromHttp();

        mainLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataRetrieval.getPhotosFromHttp();
            }
        });
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

        if(photoAdapter == null) {
            photoAdapter = new PhotoAdapter();
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(photoAdapter);
    }
}