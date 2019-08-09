package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.model.UserView
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
import org.junit.Assert.assertEquals
import org.junit.Test

class UserViewMapperTest {

    private val mapper = UserViewMapper()

    @Test
    fun mapToViewMapsDataCorrectly() {
        val user = generateUser()
        val userView = mapper.mapToView(user)

        assertEqualsData(user, userView)
    }

    private fun assertEqualsData(
        user: User,
        userView: UserView
    ) {
        assertEquals(user.id, userView.id)
        assertEquals(user.name, userView.name)
        assertEquals(user.email, userView.email)
    }
}