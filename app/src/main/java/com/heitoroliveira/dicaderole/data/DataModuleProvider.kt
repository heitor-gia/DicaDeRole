package com.heitoroliveira.dicaderole.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.heitoroliveira.dicaderole.R
import com.heitoroliveira.dicaderole.data.events.EventsRepository
import com.heitoroliveira.dicaderole.data.events.EventsRepositoryImpl
import com.heitoroliveira.dicaderole.data.events.remote.EventsApi
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataModuleProvider {
    fun getModule() = module {
        //Gson
        factory { GsonBuilder().create() }

        //Retrofit
        single {
            Retrofit.Builder()
                .baseUrl(get<Context>().getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        //EventApi
        single {
            get<Retrofit>().create(EventsApi::class.java)
        }

        //EventsRepository
        single {
            EventsRepositoryImpl(get())
        } bind EventsRepository::class
    }
}