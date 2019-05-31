package com.heitoroliveira.dicaderole.data.events.remote

import com.heitoroliveira.dicaderole.data.events.remote.model.CuponResponseModel
import com.heitoroliveira.dicaderole.data.events.remote.model.EventResponseModel
import com.heitoroliveira.dicaderole.data.events.remote.model.PersonResponseModel
import com.heitoroliveira.dicaderole.domain.events.entity.Cupon
import com.heitoroliveira.dicaderole.domain.events.entity.Event
import com.heitoroliveira.dicaderole.domain.events.entity.Person
import io.reactivex.functions.Function

object EventDataMapper : Function<EventResponseModel,Event>{
    override fun apply(model: EventResponseModel) = Event(
        id = model.id,
        title = model.title,
        date = model.date,
        description = model.description,
        image = model.image,
        latitude = model.latitude,
        longitude = model.longitude,
        price = model.price,
        people = model.people.map(PersonDataMapper::apply),
        cupons = model.cupons.map(CuponDataMapper::apply)
    )
}

object PersonDataMapper : Function<PersonResponseModel, Person> {
    override fun apply(model: PersonResponseModel) = Person(
        id = model.id,
        name = model.name,
        picture = model.picture,
        eventId = model.eventId
    )
}

object CuponDataMapper : Function<CuponResponseModel, Cupon>{
    override fun apply(model: CuponResponseModel) = Cupon(
        id = model.id,
        discount = model.discount,
        eventId = model.eventId
    )

}