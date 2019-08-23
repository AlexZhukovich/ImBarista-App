package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.exception.AuthException
import com.alexzh.imbarista.domain.interactor.user.LogOut
import com.alexzh.imbarista.mapper.AuthViewExceptionMapper
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver

class LogOutViewModel(
    private val logOut: LogOut,
    private val authViewExceptionMapper: AuthViewExceptionMapper
) : ViewModel() {
    private val logOutLiveData: MutableLiveData<Resource<Any>> = MutableLiveData()

    fun getLogOutInfo(): LiveData<Resource<Any>> = logOutLiveData

    fun logOut() {
        logOutLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        logOut.execute(
            LogOutSubscriber()
        )
    }

    override fun onCleared() {
        logOut.dispose()
        super.onCleared()
    }

    private inner class LogOutSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            logOutLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                null,
                null
            ))
        }

        override fun onError(error: Throwable) {
            val newError = if (error is AuthException) {
                authViewExceptionMapper.mapToView(error)
            } else {
                error
            }

            logOutLiveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                newError
            ))
        }
    }
}