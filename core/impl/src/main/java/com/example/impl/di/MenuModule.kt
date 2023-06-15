package com.example.impl.di

import com.example.api.IMainLauncher
import com.example.impl.presentation.interfaces.impl.MainLauncherImpl
import org.koin.dsl.module

val menuIdentificationModule = module {
    factory<IMainLauncher> { MainLauncherImpl() }
}