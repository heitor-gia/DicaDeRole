package com.heitoroliveira.dicaderole.domain.events.entity

data class Event(
    val id: String,
    val title: String,
    val date: Long,
    val image: String,
    val price: Double,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val cupons: List<Cupon>,
    val people: List<Person>
)