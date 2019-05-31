package com.heitoroliveira.dicaderole.presentation.core.extensions

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object SimpleBrazilianDateFormatter : SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

fun Long.toDate() = Date(this)

fun Date.toBrazilianFormattedDate(): String = SimpleBrazilianDateFormatter.format(this.time)

fun Double.toCurrency(): String {
    val locale = Locale("pt", "BR")
    val currencyInstance = NumberFormat.getCurrencyInstance(locale)
    return currencyInstance.format(this)
}