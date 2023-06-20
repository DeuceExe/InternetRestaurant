package com.example.impl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val dishes: ArrayList<Dishes>
) : Parcelable

@Parcelize
data class Dishes(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val image_url: String,
    val tegs: List<String>
): Parcelable