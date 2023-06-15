package com.example.internetrestaurant.di

import com.example.internetrestaurant.ResourcesProvider
import org.koin.core.module.Module
import org.koin.dsl.module

fun createUtilsModule(): Module = module {
    single { ResourcesProvider(get()) }
}