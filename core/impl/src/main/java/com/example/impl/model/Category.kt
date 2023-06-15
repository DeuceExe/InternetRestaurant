package com.example.impl.model

data class Category(
    val categories : List<Categories>
)

data class Categories(
    val id: Int,
    val name: String,
    val image_url: String
)