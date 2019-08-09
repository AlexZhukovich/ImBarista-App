package com.alexzh.imbarista.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.interactor.user.LogOut
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver

class ProfileViewModel(
    private val logOut: LogOut
): ViewModel() {
    private val logOutLiveData:MutableLiveData<Resource<Any>> = MutableLiveData()

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

    private inner class LogOutSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            logOutLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                null,
                null
            ))
        }

        override fun onError(error: Throwable) {
               logOutLiveData.postValue(Resource(
                   ResourceState.ERROR,
                   null,
                   error
               ))
        }
    }
}