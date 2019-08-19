package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.remote.model.UserModel
import com.alexzh.imbarista.commonandroidtestdata.remote.GenerateRemoteTestData.generateUserModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    private val mapper = UserMapper()

    @Test
    fun mapFromModelMapsDataCorrectly() {
        val userModel = generateUserModel()
        val userEntity = mapper.mapFromModel(userModel)

        assertEqualsData(userModel, userEntity)
    }

    private fun assertEqualsData(
        userModel: UserModel,
        userEntity: UserEntity
    ) {
        assertEquals(userModel.id, userEntity.id)
        assertEquals(userModel.name, userEntity.name)
        assertEquals(userModel.email, userEntity.email)
    }
}