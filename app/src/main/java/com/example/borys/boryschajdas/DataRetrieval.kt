package com.example.borys.boryschajdas

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

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

val photosService = Retrofit.Builder()
        .baseUrl(BASE_SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PhotosService::class.java)