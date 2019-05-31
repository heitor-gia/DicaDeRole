package com.heitoroliveira.dicaderole.domain.core

import android.util.Patterns

fun String.isAValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
