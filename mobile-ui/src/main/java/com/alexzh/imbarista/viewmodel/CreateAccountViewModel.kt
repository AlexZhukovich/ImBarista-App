package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.R
import com.alexzh.imbarista.domain.interactor.user.CreateAccount
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.model.UserAlreadyExistException
import com.alexzh.imbarista.ext.isValidEmail
import com.alexzh.imbarista.mapper.UserAlreadyExistExceptionViewMapper
import com.alexzh.imbarista.mapper.UserViewMapper
import com.alexzh.imbarista.model.UserView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class CreateAccountViewModel(
    private val createAccount: CreateAccount,
    private val mapper: UserViewMapper
) : ViewModel() {

    companion object {
        private const val INVALID_RESOURCE_VALUE = -1
    }

    private val userLiveData: MutableLiveData<Resource<UserView>> = MutableLiveData()
    private val nameErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val emailErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val passwordErrorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getUserInfo(): LiveData<Resource<UserView>> = userLiveData

    fun getNameError(): LiveData<Int> = nameErrorLiveData

    fun getEmailError(): LiveData<Int> = emailErrorLiveData

    fun getPasswordError(): LiveData<Int> = passwordErrorLiveData

    fun createAccount(name: String, email: String, password: String) {
        userLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        createAccount.execute(
            CreateAccountSubscriber(),
            CreateAccount.Param.forCreatingAccount(name, email, password)
        )
    }

    override fun onCleared() {
        createAccount.dispose()
        super.onCleared()
    }

    fun changeNameText(name: String) {
        when {
            name.isBlank() -> nameErrorLiveData.postValue(R.string.error_name_is_blank)
            else -> nameErrorLiveData.postValue(INVALID_RESOURCE_VALUE)
        }
    }

    fun changeEmailText(email: String) {
        when {
            email.isBlank() -> emailErrorLiveData.postValue(R.string.error_email_is_blank)
            !email.isValidEmail() -> emailErrorLiveData.postValue(R.string.error_email_is_not_email)
            else -> emailErrorLiveData.postValue(INVALID_RESOURCE_VALUE)
        }
    }

    fun changePasswordText(password: String) {
        when {
            password.isBlank() -> passwordErrorLiveData.postValue(R.string.error_password_is_blank)
            else -> passwordErrorLiveData.postValue(INVALID_RESOURCE_VALUE)
        }
    }

    private inner class CreateAccountSubscriber : DisposableSingleObserver<User>() {
        override fun onSuccess(user: User) {
            userLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                mapper.mapToView(user),
                null
            ))
        }

        override fun onError(error: Throwable) {
            val newError = if (error is UserAlreadyExistException) {
                UserAlreadyExistExceptionViewMapper().mapToView(error)
            } else {
                error
            }

            userLiveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                newError
            ))
        }
    }
}