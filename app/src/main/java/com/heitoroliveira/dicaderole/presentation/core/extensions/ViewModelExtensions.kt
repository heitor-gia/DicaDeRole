package com.heitoroliveira.dicaderole.presentation.core.extensions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.heitoroliveira.dicaderole.domain.core.CompletableUseCase
import com.heitoroliveira.dicaderole.domain.core.NoParam
import com.heitoroliveira.dicaderole.domain.core.SingleUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

infix fun <I, O> ViewModel.execute(useCase: SingleUseCase<I, O>) =
    SingleUseCaseExecution(useCase)

infix fun <O> ViewModel.executeWithNoParam(useCase: SingleUseCase<NoParam, O>) =
    SingleUseCaseExecution(useCase) with NoParam


class SingleUseCaseExecution<I, O>(val baseUseCase: SingleUseCase<I, O>) {
    infix fun with(params: I) = baseUseCase.run(params)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

infix fun <I> ViewModel.execute(useCase: CompletableUseCase<I>) =
    CompletableUseCaseExecution(useCase)

infix fun ViewModel.executeWithNoParam(useCase: CompletableUseCase<NoParam>) =
    CompletableUseCaseExecution(useCase) with NoParam


class CompletableUseCaseExecution<I>(val baseUseCase: CompletableUseCase<I>) {
    infix fun with(params: I) = baseUseCase.run(params)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> liveData(execute: MutableLiveData<T>.() -> Unit = {}) = lazy {
    MutableLiveData<T>().apply {
        execute()
    }
}