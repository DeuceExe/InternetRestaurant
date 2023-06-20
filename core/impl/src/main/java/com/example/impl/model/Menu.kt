package com.example.impl.model

data class Menu(
    val dishes: List<Dishes>
)

data class Dishes(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val image_url: String,
    val tegs: List<String>
)