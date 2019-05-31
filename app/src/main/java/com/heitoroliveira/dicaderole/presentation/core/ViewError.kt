package com.heitoroliveira.dicaderole.presentation.core

sealed class ViewError {
    class ErrorWithStringResourceMessage(val resId:Int):ViewError()
    object ErrorOnLoadData : ViewError()
}