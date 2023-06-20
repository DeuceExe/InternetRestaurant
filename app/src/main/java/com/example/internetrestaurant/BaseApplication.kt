package com.example.internetrestaurant

import android.app.Application
import com.example.impl.di.menuIdentificationModule
import com.example.internetrestaurant.di.createRetrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

class BaseApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        insertKoin(this, initModules())
    }

    private fun initModules(): List<Module> = listOf(
        createRetrofitModule(),
        menuIdentificationModule
    )

    private fun insertKoin(application: Application, moduleList: List<Module>) {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(moduleList)
        }
    }

    companion object {

        const val BASE_URL = "https://run.mocky.io/"
    }
}