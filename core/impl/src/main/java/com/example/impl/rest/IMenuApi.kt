package com.example.impl.rest

import com.example.impl.model.Menu
import retrofit2.Response
import retrofit2.http.GET

interface IMenuApi {

    @GET("v3/aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getMenu(): Response<Menu>
}