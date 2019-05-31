package com.heitoroliveira.dicaderole.presentation.eventdetail.model

import android.location.Location

data class EventDetail(
    val id: String,
    val title: String,
    val image: String,
    val price: String,
    val date: String,
    val description: String,
    val location: Location,
    val organizers: List<Organizer>,
    val discount: String
)