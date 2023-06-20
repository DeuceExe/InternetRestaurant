package com.example.impl.rest

import com.example.impl.model.Dishes

interface IOrderInfo {
    fun onOrderDataReceived(dish: MutableList<Dishes>)
}