package com.example.impl.di

import com.example.impl.presentation.dialog.CustomDialog
import com.example.impl.presentation.fragments.bucketFragment.BucketFragment
import com.example.impl.presentation.fragments.bucketFragment.BucketViewModel
import com.example.impl.presentation.fragments.categoryFragment.CategoryFragment
import com.example.impl.presentation.fragments.categoryFragment.CategoryViewModel
import com.example.impl.presentation.fragments.dishesFragment.DishesFragment
import com.example.impl.presentation.fragments.dishesFragment.DishesViewModel
import com.example.impl.rest.IMenuApi
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal val viewModelModule = module {
    single { get<Retrofit>().create(IMenuApi::class.java) }
    viewModel { BucketViewModel() }
    viewModel { CategoryViewModel(get()) }
    viewModel { DishesViewModel(get()) }
}

internal val menuAppModule = module {
    single { CustomDialog(get()) }
    fragment { BucketFragment() }
    fragment { CategoryFragment() }
    fragment { DishesFragment() }
}