package com.heitoroliveira.dicaderole.presentation.events

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EventListActivityModuleProvider {
    fun getModule() = module {
        viewModel { EventListViewModel(get()) }
        scope(named<EventListActivity>()){
            scoped { EventItemAdapter() }
        }
    }
}