package com.heitoroliveira.dicaderole.data.events.remote.model

data class CheckinRequestModel (
    val eventId: String,
    val name: String,
    val email: String
)