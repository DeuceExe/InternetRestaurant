package com.example.impl.presentation.interfaces.impl

import com.example.api.ICategoryFragment
import com.example.api.ICategoryLauncher
import com.example.impl.di.menuAppModule
import com.example.impl.di.viewModelModule
import com.example.impl.presentation.fragments.categoryFragment.CategoryFragment
import org.koin.core.context.loadKoinModules

internal class CategoryLauncherImpl : ICategoryLauncher {

    private val businessKoin by lazy {
        listOf(
            viewModelModule,
            menuAppModule
        )
    }

    override fun launch(): ICategoryFragment {
        loadKoinModules(businessKoin)
        return CategoryFragment.build()
    }
}