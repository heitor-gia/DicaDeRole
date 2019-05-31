package com.heitoroliveira.dicaderole.presentation.core

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
    protected var compositeDisposable = CompositeDisposable()
        private set

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
            compositeDisposable = CompositeDisposable()
        }
        super.onCleared()
    }

}