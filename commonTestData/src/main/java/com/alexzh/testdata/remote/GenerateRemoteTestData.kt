package com.alexzh.testdata.remote

import com.alexzh.imbarista.remote.model.IngredientModel
import com.alexzh.testdata.base.RandomData.randomString

object GenerateRemoteTestData {

    fun generateIngredientModel() : IngredientModel {
        return IngredientModel(randomString())
    }
}