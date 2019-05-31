package com.heitoroliveira.dicaderole.domain.events.usecase

import com.heitoroliveira.dicaderole.data.events.EventsRepository
import com.heitoroliveira.dicaderole.domain.core.SingleUseCase
import com.heitoroliveira.dicaderole.domain.events.entity.Event

typealias Id = String

class GetEventById(
    private val eventsRepository: EventsRepository
) : SingleUseCase<Id,Event> {

    override fun run(input: Id) =
        eventsRepository
            .getEvent(input)

}