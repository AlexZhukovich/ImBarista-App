package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.AuthUserEntity
import com.alexzh.imbarista.remote.model.AuthUserModel
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateAuthUserModel
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthUserMapperTest {

    private val mapper = AuthUserMapper()

    @Test
    fun mapFromModelMapsDataCorrectly() {
        val authUserModel = generateAuthUserModel()
        val authUserEntity = mapper.mapFromModel(authUserModel)

        assertEqualsData(authUserModel, authUserEntity)
    }

    private fun assertEqualsData(
        authUserModel: AuthUserModel,
        authUserEntity: AuthUserEntity
    ) {
        assertEquals(authUserModel.id, authUserEntity.id)
        assertEquals(authUserModel.name, authUserEntity.name)
        assertEquals(authUserModel.token, authUserEntity.token)
    }
}