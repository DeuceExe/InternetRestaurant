package com.example.impl.rest

import com.example.impl.model.Category
import com.example.impl.model.Menu
import retrofit2.Response
import retrofit2.http.GET

interface IMenuApi {

    @GET("v3/aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getDishes(): Response<Menu>

     @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategory(): Response<Category>
}