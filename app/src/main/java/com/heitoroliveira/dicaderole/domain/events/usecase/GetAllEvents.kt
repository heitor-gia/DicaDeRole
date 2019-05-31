package com.heitoroliveira.dicaderole.domain.events.usecase

import com.heitoroliveira.dicaderole.data.events.EventsRepository
import com.heitoroliveira.dicaderole.domain.core.NoParam
import com.heitoroliveira.dicaderole.domain.core.SingleUseCase
import com.heitoroliveira.dicaderole.domain.events.entity.Event

class GetAllEvents(
    private val eventsRepository: EventsRepository
) : SingleUseCase<NoParam,List<Event>> {

    override fun run(input: NoParam) =
        eventsRepository
            .getEvents()

}