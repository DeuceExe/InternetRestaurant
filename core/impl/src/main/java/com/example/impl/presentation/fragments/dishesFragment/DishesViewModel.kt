package com.example.impl.presentation.fragments.dishesFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.impl.model.Dishes
import com.example.impl.rest.IMenuApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class DishesViewModel(private val serviceApi: IMenuApi) : ViewModel(), KoinComponent {

    private val _dishes = MutableLiveData<List<Dishes>>()
    val dishes: LiveData<List<Dishes>> get() = _dishes
    
    suspend fun getDishes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = serviceApi.getDishes()
                if (result.isSuccessful) {
                    _dishes.postValue(result.body()?.dishes)
                }
            }
        }
    }

    private fun sortByTag(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

            }
        }
    }
}