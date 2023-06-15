package com.example.impl.model

data class Menu(
    val dishes: List<Dish>
)

data class Dish(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val image_url: String,
    val tegs: List<Teg>
)

data class Teg(
    val teg_name: List<String>
)