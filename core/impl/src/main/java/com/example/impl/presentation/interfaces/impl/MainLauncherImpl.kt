package com.example.impl.presentation.interfaces.impl

import com.example.api.IMainFragment
import com.example.api.IMainLauncher
import com.example.impl.di.filmAppModule
import com.example.impl.di.viewModelModule
import com.example.impl.presentation.fragments.mainFragment.MainFragment
import org.koin.core.context.loadKoinModules

internal class MainLauncherImpl : IMainLauncher {

    private val businessKoin by lazy {
        listOf(
            viewModelModule,
            filmAppModule
        )
    }

    override fun launch(): IMainFragment {
        loadKoinModules(businessKoin)
        return MainFragment.build()
    }
}