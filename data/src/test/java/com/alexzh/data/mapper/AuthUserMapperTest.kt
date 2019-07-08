package com.alexzh.data.mapper

import com.alexzh.data.model.AuthUserEntity
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.testdata.data.GenerateDataTestData.generateAuthUserEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateAuthUser
import org.junit.Assert
import org.junit.Test

class AuthUserMapperTest {

    private val mapper = AuthUserMapper()

    @Test
    fun mapFromEntityToDomainModelCorrectly() {
        val authUserEntity = generateAuthUserEntity()
        val authUser = mapper.mapFromEntity(authUserEntity)

        assertEqualsData(authUser, authUserEntity)
    }

    @Test
    fun mapToEntityFromDomainModelCorrectly() {
        val authUser = generateAuthUser()
        val authUserEntity = mapper.mapToEntity(authUser)

        assertEqualsData(authUser, authUserEntity)
    }

    private fun assertEqualsData(
        authUser: AuthUser,
        authUserEntity: AuthUserEntity
    ) {
        Assert.assertEquals(authUser.name, authUserEntity.name)
        Assert.assertEquals(authUser.token, authUserEntity.token)
    }
}