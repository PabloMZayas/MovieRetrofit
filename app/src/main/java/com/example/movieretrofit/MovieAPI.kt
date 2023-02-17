package com.example.movieretrofit

import retrofit2.Call
import retrofit2.http.GET

interface UsersApi {

    @GET ("users")
    fun getUsers(): Call<List<UsersItem>>
}