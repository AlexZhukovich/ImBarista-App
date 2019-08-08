package com.alexzh.imbarista.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.interactor.user.LogIn
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.mapper.SessionViewModelMapper
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class LoginViewModel(
    private val logIn: LogIn,
    private val mapper: SessionViewModelMapper
): ViewModel() {

    private val liveData: MutableLiveData<Resource<SessionView>> = MutableLiveData()

    fun getLogInInfo(): LiveData<Resource<SessionView>> = liveData

    fun logIn(email: String, password: String) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))

        logIn.execute(
            LogInSubscriber(),
            LogIn.Param.forLogIn(email, password)
        )
    }

    override fun onCleared() {
        logIn.dispose()
        super.onCleared()
    }

    private inner class LogInSubscriber: DisposableSingleObserver<Session>() {
        override fun onSuccess(session: Session) {
            liveData.postValue(Resource(
                ResourceState.SUCCESS,
                mapper.mapToView(session),
                null
            ))
        }

        override fun onError(error: Throwable) {
            liveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                error
            ))
        }
    }
}