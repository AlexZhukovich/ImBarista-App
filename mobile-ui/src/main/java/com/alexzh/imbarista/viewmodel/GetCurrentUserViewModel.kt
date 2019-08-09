package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.interactor.user.GetCurrentUser
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.mapper.UserViewMapper
import com.alexzh.imbarista.model.UserView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class GetCurrentUserViewModel(
    private val getCurrentUser: GetCurrentUser,
    private val mapper: UserViewMapper
) : ViewModel() {

    private val userLiveData: MutableLiveData<Resource<UserView>> = MutableLiveData()

    fun getUserInfo(): LiveData<Resource<UserView>> = userLiveData

    fun fetchUserInfo() {
        userLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        getCurrentUser.execute(
            GetCurrentUserInfoSubscriber()
        )
    }

    override fun onCleared() {
        getCurrentUser.dispose()
        super.onCleared()
    }

    private inner class GetCurrentUserInfoSubscriber : DisposableSingleObserver<User>() {
        override fun onSuccess(user: User) {
            userLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                mapper.mapToView(user),
                null
            ))
        }

        override fun onError(error: Throwable) {
            userLiveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                error
            ))
        }
    }
}