package com.android.example.testapplication.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import com.android.example.testapplication.network.User
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    @GET("users")
    suspend fun getUsers():List<User>
}


object UsersApi{

    val retrofitService: UserApiService by lazy{
        retrofit.create(UserApiService::class.java)
    }
}