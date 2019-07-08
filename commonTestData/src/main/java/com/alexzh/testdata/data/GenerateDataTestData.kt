package com.alexzh.testdata.data

import com.alexzh.data.model.AuthUserEntity
import com.alexzh.testdata.base.RandomData

object GenerateDataTestData {

    fun generateAuthUserEntity(): AuthUserEntity {
        return AuthUserEntity(
            name = RandomData.randomString(),
            token = RandomData.randomString()
        )
    }
}