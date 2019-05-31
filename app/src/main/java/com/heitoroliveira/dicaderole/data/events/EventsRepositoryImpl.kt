package com.heitoroliveira.dicaderole.data.events

import com.heitoroliveira.dicaderole.data.events.remote.EventDataMapper
import com.heitoroliveira.dicaderole.data.events.remote.EventsApi
import com.heitoroliveira.dicaderole.data.events.remote.model.CheckinRequestModel
import com.heitoroliveira.dicaderole.data.exceptions.UnableToLoadDataException
import io.reactivex.Completable

class EventsRepositoryImpl(
    private val eventsApi: EventsApi
) : EventsRepository {

    override fun checkin(eventId: String, name: String, email: String) =
        eventsApi.checkin(CheckinRequestModel(eventId, name, email))
            .flatMapCompletable {
                when (it.code.toInt()) {
                    in 200..299 -> Completable.complete()
                    else -> Completable.error(UnableToLoadDataException())
                }
            }

    override fun getEvents() =
        eventsApi.getEvents()
            .map { it.map(EventDataMapper::apply) }

    override fun getEvent(id: String) =
        eventsApi.getEvent(id)
            .map(EventDataMapper)
}