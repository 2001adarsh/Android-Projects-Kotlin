package com.adarsh.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<POST>>
}