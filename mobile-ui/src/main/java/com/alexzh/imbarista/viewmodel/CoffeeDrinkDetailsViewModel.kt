package com.alexzh.imbarista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinkById
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.AddCoffeeDrinkToFavourites
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.RemoveCoffeeDrinkFromFavourite
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.mapper.CoffeeDrinkViewMapper
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver

class CoffeeDrinkDetailsViewModel(
    private val getCoffeeDrinkById: GetCoffeeDrinkById,
    private val addCoffeeDrinkToFavourites: AddCoffeeDrinkToFavourites,
    private val removeCoffeeDrinkFromFavourite: RemoveCoffeeDrinkFromFavourite,
    private val coffeeDrinkViewMapper: CoffeeDrinkViewMapper
) : ViewModel() {

    private var coffeeDrinkId = -1L
    private val coffeeDrinkLiveData: MutableLiveData<Resource<CoffeeDrinkView>> = MutableLiveData()

    fun getCoffeeDrinkInfo(): LiveData<Resource<CoffeeDrinkView>> = coffeeDrinkLiveData

    fun fetchCoffeeDrink(coffeeDrinkId: Long) {
        this.coffeeDrinkId = coffeeDrinkId
        coffeeDrinkLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        getCoffeeDrinkById.execute(
            CoffeeDrinkSubscriber(),
            GetCoffeeDrinkById.Param.forCoffee(coffeeDrinkId)
        )
    }

    fun updateDrinkCoffeeData(isFavourite: Boolean) {
        coffeeDrinkLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        if (isFavourite) {
            addCoffeeDrinkToFavourites.execute(
                CoffeeDrinkSubscriber(),
                AddCoffeeDrinkToFavourites.Param.forCoffeeDrink(coffeeDrinkId)
            )
        } else {
            removeCoffeeDrinkFromFavourite.execute(
                CoffeeDrinkSubscriber(),
                RemoveCoffeeDrinkFromFavourite.Param.forCoffeeDrink(coffeeDrinkId)
            )
        }
    }

    override fun onCleared() {
        getCoffeeDrinkById.dispose()
        addCoffeeDrinkToFavourites.dispose()
        removeCoffeeDrinkFromFavourite.dispose()
        super.onCleared()
    }

    private inner class CoffeeDrinkSubscriber : DisposableSingleObserver<CoffeeDrink>() {

        override fun onSuccess(coffeeDrink: CoffeeDrink) {
            coffeeDrinkLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                coffeeDrinkViewMapper.mapToView(coffeeDrink),
                null
            ))
        }

        override fun onError(error: Throwable) {
            coffeeDrinkLiveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                error
            ))
        }
    }
}