package com.heitoroliveira.dicaderole.presentation.eventdetail.checkin

import com.heitoroliveira.dicaderole.presentation.core.ViewError

sealed class CheckinStatus {
    object Idle : CheckinStatus()
    object Completed : CheckinStatus()
    object Loading : CheckinStatus()
    class Error(val viewError: ViewError) : CheckinStatus()
}

