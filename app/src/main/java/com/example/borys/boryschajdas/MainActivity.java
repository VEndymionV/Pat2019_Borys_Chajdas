package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends Activity {

    public final static long RETRY_CONNECTION_TIME = 5000L;
    public final static short NUMBER_OF_RETRY_ATTEMPTS = 10;

    private int retryCount = 0;

    private User user;

    private PhotoAdapter photoAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
                List<Photo> photos;

                if (photoObject != null)
                    photos = photoObject.getArray();
                else
                    photos = null;

                if (photos != null)
                    photoAdapter.updateItems(photos);

                findViewById(R.id.photosLoading_progressBar).setVisibility(View.GONE);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PhotoObject> call, Throwable t) {

                Log.e("photosService", t.getLocalizedMessage());
                Log.d("photosService", "Can't download photos. Attempt: " + retryCount + ", Attempt limit: " + NUMBER_OF_RETRY_ATTEMPTS);

                if (retryCount++ < NUMBER_OF_RETRY_ATTEMPTS) {

                    Handler retryConnectionHandler = new Handler();
                    retryConnectionHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getPhotosFromHttp();
                        }
                    }, RETRY_CONNECTION_TIME);
                }
                else{
                    Log.d("photosService", "Can't download photos. The number of attempts allowed has been exceeded.");
                }
            }
        });
    }
}
