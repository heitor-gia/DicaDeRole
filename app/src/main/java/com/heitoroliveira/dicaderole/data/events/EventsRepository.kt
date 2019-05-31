package com.heitoroliveira.dicaderole.data.events

import com.heitoroliveira.dicaderole.domain.events.entity.Event
import io.reactivex.Completable
import io.reactivex.Single

interface EventsRepository {

    fun checkin(eventId: String, name: String, email: String) : Completable

    fun getEvents() : Single<List<Event>>

    fun getEvent(id: String) : Single<Event>
}