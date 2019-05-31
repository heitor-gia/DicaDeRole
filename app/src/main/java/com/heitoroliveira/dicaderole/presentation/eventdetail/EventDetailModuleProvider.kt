package com.heitoroliveira.dicaderole.presentation.eventdetail

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EventDetailModuleProvider {
    fun getModule() = module {
        viewModel { EventDetailViewModel(getEventById = get(), doCheckin = get()) }
        scope(named<EventDetailActivity>()){
            scoped { OrganizersListAdapter() }
        }
    }
}