package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinks
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.mapper.CoffeeDrinkViewMapper
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class GetCoffeeDrinksViewModel(
    private val getCoffeeDrinks: GetCoffeeDrinks,
    private val coffeeDrinkViewMapper: CoffeeDrinkViewMapper
) : ViewModel() {

    private val coffeeDrinksLiveData: MutableLiveData<Resource<List<CoffeeDrinkView>>> = MutableLiveData()

    fun getCoffeeDrinks(): LiveData<Resource<List<CoffeeDrinkView>>> = coffeeDrinksLiveData

    fun fetchCoffeeDrinks() {
        coffeeDrinksLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        getCoffeeDrinks.execute(
            GetCoffeeDrinksSubscriber()
        )
    }

    override fun onCleared() {
        getCoffeeDrinks.dispose()
        super.onCleared()
    }

    private inner class GetCoffeeDrinksSubscriber : DisposableSingleObserver<List<CoffeeDrink>>() {
        override fun onSuccess(coffeeDrinks: List<CoffeeDrink>) {
            coffeeDrinksLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                coffeeDrinks.map { coffeeDrinkViewMapper.mapToView(it) },
                null
            ))
        }

        override fun onError(error: Throwable) {
            coffeeDrinksLiveData.postValue(
                Resource(
                ResourceState.ERROR,
                null,
                error
            )
            )
        }
    }
}
