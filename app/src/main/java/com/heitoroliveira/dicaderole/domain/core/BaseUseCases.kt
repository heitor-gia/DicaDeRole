package com.heitoroliveira.dicaderole.domain.core

import io.reactivex.Completable
import io.reactivex.Single

interface BaseUseCase<in Input, out Output> {
    fun run(input: Input) : Output
}

interface SingleUseCase<in Input, Output> : BaseUseCase<Input, Single<Output>>

interface CompletableUseCase<in Input> : BaseUseCase<Input,Completable>

object NoParam