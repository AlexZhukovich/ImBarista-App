package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.interactor.user.GetExistingSession
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.mapper.SessionViewMapper
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class CheckExistingSessionViewModel(
    private val getExistingSession: GetExistingSession,
    private val mapper: SessionViewMapper
) : ViewModel() {

    private val sessionLiveData: MutableLiveData<Resource<SessionView>> = MutableLiveData()

    fun getExistingSessionInfo(): LiveData<Resource<SessionView>> = sessionLiveData

    fun checkExistingSession() {
        sessionLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        getExistingSession.execute(
            GetExistingSessionSubscriber()
        )
    }

    override fun onCleared() {
        getExistingSession.dispose()
        super.onCleared()
    }

    private inner class GetExistingSessionSubscriber : DisposableSingleObserver<Session>() {

        override fun onSuccess(session: Session) {
            sessionLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                mapper.mapToView(session),
                null
            ))
        }

        override fun onError(error: Throwable) {
            sessionLiveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                error
            ))
        }
    }
}