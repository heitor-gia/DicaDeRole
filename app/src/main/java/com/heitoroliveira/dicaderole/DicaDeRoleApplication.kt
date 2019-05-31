package com.heitoroliveira.dicaderole

import android.app.Application
import com.heitoroliveira.dicaderole.data.DataModuleProvider
import com.heitoroliveira.dicaderole.inject.UseCaseModuleProvider
import com.heitoroliveira.dicaderole.presentation.eventdetail.EventDetailModuleProvider
import com.heitoroliveira.dicaderole.presentation.events.EventListActivityModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DicaDeRoleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@DicaDeRoleApplication)
            modules(
                listOf(
                    UseCaseModuleProvider.getModule(),
                    DataModuleProvider.getModule(),
                    EventDetailModuleProvider.getModule(),
                    EventListActivityModuleProvider.getModule()
                )
            )
        }
    }


}