package com.example.impl.di

import com.example.impl.presentation.fragments.bucketFragment.BucketFragment
import com.example.impl.presentation.fragments.bucketFragment.BucketViewModel
import com.example.impl.presentation.fragments.mainFragment.MainFragment
import com.example.impl.presentation.fragments.mainFragment.MainViewModel
import com.example.impl.rest.IMenuApi
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal val viewModelModule = module {
    single { get<Retrofit>().create(IMenuApi::class.java) }
    viewModel { BucketViewModel(get()) }
    viewModel { MainViewModel(get()) }
}

internal val filmAppModule = module {
    fragment { BucketFragment() }
    fragment { MainFragment() }
}