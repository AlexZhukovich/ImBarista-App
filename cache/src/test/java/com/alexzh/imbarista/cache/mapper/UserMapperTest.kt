package com.alexzh.imbarista.cache.mapper

import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.cache.model.User
import com.alexzh.imbarista.commonandroidtestdata.cache.GenerateCacheTestData.generateUser
import com.alexzh.testdata.data.GenerateDataTestData.generateUserEntity
import org.junit.Assert
import org.junit.Test

class UserMapperTest {

    private val mapper = UserMapper()

    @Test
    fun mapFromEntityToCacheModelCorrectly() {
        val userEntity = generateUserEntity()
        val user = mapper.mapToCached(userEntity)

        assertEqualsData(user, userEntity)
    }

    @Test
    fun mapFromCacheModelToEntityCorrectly() {
        val user = generateUser()
        val userEntity = mapper.mapFromCached(user)

        assertEqualsData(user, userEntity)
    }

    private fun assertEqualsData(
        user: User,
        userEntity: UserEntity
    ) {
        Assert.assertEquals(user.id, userEntity.id)
        Assert.assertEquals(user.name, userEntity.name)
        Assert.assertEquals(user.email, userEntity.email)
    }
}