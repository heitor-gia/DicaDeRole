package com.heitoroliveira.dicaderole.inject

import com.heitoroliveira.dicaderole.domain.checkin.usecases.DoCheckin
import com.heitoroliveira.dicaderole.domain.events.usecase.GetAllEvents
import com.heitoroliveira.dicaderole.domain.events.usecase.GetEventById
import org.koin.dsl.module

object UseCaseModuleProvider {
    fun getModule() = module {
        factory { GetAllEvents(get()) }
        factory { GetEventById(get()) }
        factory { DoCheckin(get()) }
    }
}