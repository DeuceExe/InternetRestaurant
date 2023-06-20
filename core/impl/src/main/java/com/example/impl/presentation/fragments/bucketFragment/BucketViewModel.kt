package com.example.impl.presentation.fragments.bucketFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.impl.model.Dishes
import com.example.impl.rest.IOrderInfo
import org.koin.core.component.KoinComponent

class BucketViewModel : ViewModel(), IOrderInfo, KoinComponent {
    private val _orderList = MutableLiveData<List<Dishes>>()
    val orderList: LiveData<List<Dishes>> get() = _orderList

    override fun onOrderDataReceived(dish: MutableList<Dishes>) {
        _orderList.postValue(dish)
    }
}