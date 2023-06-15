package com.example.impl.di

import com.example.api.ICategoryLauncher
import com.example.impl.presentation.interfaces.impl.CategoryLauncherImpl
import org.koin.dsl.module

val menuIdentificationModule = module {
    factory<ICategoryLauncher> { CategoryLauncherImpl() }
}