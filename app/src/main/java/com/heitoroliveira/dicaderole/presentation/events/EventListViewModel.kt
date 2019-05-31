package com.heitoroliveira.dicaderole.presentation.events

import android.arch.lifecycle.MutableLiveData
import com.heitoroliveira.dicaderole.domain.events.entity.Event
import com.heitoroliveira.dicaderole.domain.events.usecase.GetAllEvents
import com.heitoroliveira.dicaderole.presentation.core.BaseViewModel
import com.heitoroliveira.dicaderole.presentation.core.ViewError
import com.heitoroliveira.dicaderole.presentation.core.extensions.*
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class EventListViewModel(
    private val getAllEvents: GetAllEvents
) : BaseViewModel() {

    val events : MutableLiveData<List<EventItem>> by liveData { loadEvents() }
    val errors : MutableLiveData<ViewError> by liveData()
    val isLoading : MutableLiveData<Boolean> by liveData{ value = false }


    fun loadEvents() {
        executeWithNoParam(getAllEvents)
            .map { it.map(EventItemMapper::apply) }
            .doOnSubscribe { isLoading.value = true }
            .doAfterTerminate { isLoading.value = false }
            .subscribeBy (
                onSuccess = { events.value = it } ,
                onError = { errors.value = ViewError.ErrorOnLoadData }
            )
            .addTo(compositeDisposable)

    }

    object EventItemMapper : Function<Event,EventItem> {
        override fun apply(event: Event) = EventItem(
            id = event.id,
            title = event.title,
            price = event.price.toCurrency(),
            date = event.date.toDate().toBrazilianFormattedDate(),
            image = event.image
        )
    }
}