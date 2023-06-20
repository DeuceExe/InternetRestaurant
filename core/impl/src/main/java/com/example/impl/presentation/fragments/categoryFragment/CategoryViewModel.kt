package com.example.impl.presentation.fragments.categoryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.impl.model.Categories
import com.example.impl.rest.IMenuApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CategoryViewModel(private val serviceApi: IMenuApi) : ViewModel(), KoinComponent {

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>> get() = _categories

    suspend fun getCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = serviceApi.getCategory()
                if (result.isSuccessful) {
                    _categories.postValue(result.body()?.categories)
                }
            }
        }
    }
}