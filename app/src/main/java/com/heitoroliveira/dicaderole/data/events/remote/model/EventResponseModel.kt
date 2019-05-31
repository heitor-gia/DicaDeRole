package com.heitoroliveira.dicaderole.data.events.remote.model


data class EventResponseModel(
    val id: String,
    val title: String,
    val date: Long,
    val image: String,
    val price: Double,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val cupons: List<CuponResponseModel>,
    val people: List<PersonResponseModel>
)

