package com.example.borys.boryschajdas

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.internal.EverythingIsNonNull

//const val BASE_SERVER_URL = "http://192.168.0.1:8080" // komputer
//const val BASE_SERVER_URL = "http://10.0.2.2:8080" // emulator
const val BASE_SERVER_URL = "http://192.168.1.128:8080" // telefon

data class Photo(
        val title: String,
        val desc: String,
        val url: String
)

data class PhotoObject(
        val array: List<Photo>
)

interface PhotosService {

    @GET("page_0.json")
    fun photoObject(): Call<PhotoObject>
}

class DataRetrieval(val eventListener: OnCustomEventListener){

    private val photosService = Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotosService::class.java)

    fun getPhotosFromHttp() {

        photosService.photoObject().enqueue(object : Callback<PhotoObject> {
            @EverythingIsNonNull
            override fun onResponse(call: Call<PhotoObject>, response: Response<PhotoObject>) {

                val photoObject = response.body()

                if (photoObject != null) {
                    eventListener.onEvent(photoObject.array)
                }
                else{
                    eventListener.onFailure()
                }

            }

            @EverythingIsNonNull
            override fun onFailure(call: Call<PhotoObject>, t: Throwable) {

                Log.e("photosService", t.localizedMessage)
                eventListener.onFailure()
            }
        })
    }
}