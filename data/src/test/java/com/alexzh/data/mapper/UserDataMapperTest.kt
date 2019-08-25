package com.alexzh.data.mapper

import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.domain.model.User
import com.alexzh.testdata.data.GenerateDataTestData.generateUserEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDataMapperTest {

    private val mapper = UserDataMapper()

    @Test
    fun mapFromEntityToDomainModelCorrectly() {
        val userEntity = generateUserEntity()
        val user = mapper.mapFromEntity(userEntity)

        assertEqualsData(user, userEntity)
    }

    @Test
    fun mapToEntityFromDomainModelCorrectly() {
        val user = generateUser()
        val userEntity = mapper.mapToEntity(user)

        assertEqualsData(user, userEntity)
    }

    private fun assertEqualsData(
        user: User,
        userEntity: UserEntity
    ) {
        assertEquals(user.id, userEntity.id)
        assertEquals(user.name, userEntity.name)
        assertEquals(user.email, userEntity.email)
    }
}