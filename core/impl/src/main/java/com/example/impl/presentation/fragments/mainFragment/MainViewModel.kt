package com.example.impl.presentation.fragments.mainFragment

import androidx.lifecycle.ViewModel
import com.example.impl.rest.IMenuApi
import org.koin.core.component.KoinComponent

class MainViewModel(private val serviceApi: IMenuApi) : ViewModel(), KoinComponent {
}