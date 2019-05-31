package com.heitoroliveira.dicaderole.data.events.remote

import com.heitoroliveira.dicaderole.data.events.remote.model.CheckinRequestModel
import com.heitoroliveira.dicaderole.data.events.remote.model.CheckinResponseModel
import com.heitoroliveira.dicaderole.data.events.remote.model.EventResponseModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    @POST("checkin")
    fun checkin(@Body request: CheckinRequestModel) : Single<CheckinResponseModel>

    @GET("events")
    fun getEvents() : Single<List<EventResponseModel>>

    @GET("events/{id}")
    fun getEvent(@Path("id") id: String) : Single<EventResponseModel>

}