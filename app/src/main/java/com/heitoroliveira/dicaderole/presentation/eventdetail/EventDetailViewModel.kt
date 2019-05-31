package com.heitoroliveira.dicaderole.presentation.eventdetail

import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.heitoroliveira.dicaderole.R
import com.heitoroliveira.dicaderole.domain.checkin.entity.CheckinIntent
import com.heitoroliveira.dicaderole.domain.checkin.exceptions.InvalidEmailException
import com.heitoroliveira.dicaderole.domain.checkin.usecases.DoCheckin
import com.heitoroliveira.dicaderole.domain.events.entity.Cupon
import com.heitoroliveira.dicaderole.domain.events.entity.Event
import com.heitoroliveira.dicaderole.domain.events.usecase.GetEventById
import com.heitoroliveira.dicaderole.presentation.core.BaseViewModel
import com.heitoroliveira.dicaderole.presentation.core.ViewError
import com.heitoroliveira.dicaderole.presentation.core.extensions.*
import com.heitoroliveira.dicaderole.presentation.eventdetail.checkin.CheckinStatus
import com.heitoroliveira.dicaderole.presentation.eventdetail.model.EventDetail
import com.heitoroliveira.dicaderole.presentation.eventdetail.model.Organizer
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class EventDetailViewModel(
    private val getEventById: GetEventById,
    private val doCheckin: DoCheckin
) : BaseViewModel() {

    val event: MutableLiveData<EventDetail> by liveData()
    val errors: MutableLiveData<ViewError> by liveData()
    val isLoading: MutableLiveData<Boolean> by liveData { value = false }
    val checkinStatus: MutableLiveData<CheckinStatus> by liveData { value = CheckinStatus.Idle }

    fun loadEvent(id: String) {
        execute(getEventById).with(id)
            .map(EventDetailMapper)
            .doOnSubscribe { isLoading.value = true }
            .doAfterTerminate { isLoading.value = false }
            .subscribeBy(
                onSuccess = { event.value = it },
                onError = {
                    errors.value = when (it) {
                        is InvalidEmailException -> ViewError.ErrorWithStringResourceMessage(R.string.invalidEmailMessage)
                        else -> ViewError.ErrorOnLoadData
                    }
                }
            )
            .addTo(compositeDisposable)
    }

    fun doCheckin(name: String, email: String) {
        execute(doCheckin)
            .with(
                CheckinIntent(
                    name = name,
                    email = email,
                    eventId = event.value?.id ?: "-1"
                )
            )
            .doOnSubscribe { checkinStatus.value = CheckinStatus.Loading }
            .subscribeBy(
                onComplete = { checkinStatus.value = CheckinStatus.Completed },
                onError = {
                    checkinStatus.value = CheckinStatus.Error(
                        when (it) {
                            is InvalidEmailException -> ViewError.ErrorWithStringResourceMessage(R.string.invalidEmailMessage)
                            else -> ViewError.ErrorOnLoadData
                        }
                    )
                }
            )
            .addTo(compositeDisposable)

    }


    object EventDetailMapper : Function<Event, EventDetail> {
        override fun apply(model: Event) = EventDetail(
            id = model.id,
            title = model.title,
            image = model.image,
            price = model.price.toCurrency(),
            date = model.date.toDate().toBrazilianFormattedDate(),
            description = model.description,
            discount = evaluateDiscount(model.cupons).toCurrency(),
            location = Location("event").apply {
                latitude = model.latitude
                longitude = model.longitude
            },
            organizers = model.people.map { Organizer(it.name, it.picture) }
        )

        private fun evaluateDiscount(cupons: List<Cupon>) = cupons.sumByDouble { it.discount }

    }
}