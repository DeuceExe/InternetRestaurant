package com.example.impl.presentation.fragments.categoryFragment

import androidx.lifecycle.ViewModel
import com.example.impl.rest.IMenuApi
import org.koin.core.component.KoinComponent

class CategoryViewModel(private val serviceApi: IMenuApi) : ViewModel(), KoinComponent {
}