package com.alexzh.imbarista.domain.interactor

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Param> constructor(
    private val postExecutionThread: PostExecutionThread
) {
    abstract fun buildCompletableUseCase(param: Param? = null): Completable

    private val disposables = CompositeDisposable()

    open fun execute(observer: DisposableCompletableObserver, param: Param? = null) {
        val completable = this.buildCompletableUseCase(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(completable.subscribeWith(observer))
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        disposables.dispose()
    }
}