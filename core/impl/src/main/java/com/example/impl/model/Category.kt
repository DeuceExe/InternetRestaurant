package com.example.impl.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("сategories") val categories : List<Categories>
)

data class Categories(
    val id: Int,
    val name: String,
    val image_url: String
)