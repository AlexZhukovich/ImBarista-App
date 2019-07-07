package com.alexzh.imbarista.domain.interactor

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Param> constructor(
    private val postExecutionThread: PostExecutionThread
) {
    abstract fun buildSingleUseCase(param: Param? = null): Single<T>

    private val disposables = CompositeDisposable()

    open fun execute(singleObserver: DisposableSingleObserver<T>, param: Param? = null) {
        val single = this.buildSingleUseCase(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(single.subscribeWith(singleObserver))
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        disposables.dispose()
    }
}