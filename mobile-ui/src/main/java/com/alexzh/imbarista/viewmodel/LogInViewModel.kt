package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.R
import com.alexzh.imbarista.domain.interactor.user.LogIn
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.ext.isValidEmail
import com.alexzh.imbarista.mapper.SessionViewMapper
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class LogInViewModel(
    private val logIn: LogIn,
    private val mapper: SessionViewMapper
): ViewModel() {

    companion object {
        private const val INVALID_RESOURCE_VALUE = -1
    }

    private val liveData: MutableLiveData<Resource<SessionView>> = MutableLiveData()
    private val emailErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val passwordErrorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getLogInInfo(): LiveData<Resource<SessionView>> = liveData

    fun getEmailError(): LiveData<Int> = emailErrorLiveData

    fun getPasswordError(): LiveData<Int> = passwordErrorLiveData

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

    fun changeEmailText(email: String) {
        when {
            email.isBlank() -> emailErrorLiveData.postValue(R.string.error_email_is_blank)
            !email.isValidEmail() -> emailErrorLiveData.postValue(R.string.error_email_is_not_email)
            else -> emailErrorLiveData.postValue(INVALID_RESOURCE_VALUE)
        }
    }

    fun changePasswordText(password: String) {
        if (password.isBlank()) {
            passwordErrorLiveData.postValue(R.string.error_password_is_blank)
        }  else {
            passwordErrorLiveData.postValue(INVALID_RESOURCE_VALUE)
        }
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