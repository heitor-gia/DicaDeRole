package com.heitoroliveira.dicaderole.domain.checkin.usecases

import com.heitoroliveira.dicaderole.data.events.EventsRepository
import com.heitoroliveira.dicaderole.domain.checkin.entity.CheckinIntent
import com.heitoroliveira.dicaderole.domain.checkin.exceptions.InvalidEmailException
import com.heitoroliveira.dicaderole.domain.core.CompletableUseCase
import com.heitoroliveira.dicaderole.domain.core.isAValidEmail
import io.reactivex.Completable

class DoCheckin(
    val eventsRepository: EventsRepository
) : CompletableUseCase<CheckinIntent> {

    override fun run(input: CheckinIntent): Completable {
        return Completable.defer{
            if (!input.email.isAValidEmail()) throw InvalidEmailException()
            eventsRepository.checkin(
                name = input.name,
                email = input.email,
                eventId = input.eventId
            )
        }
    }
}