package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.model.UserView

class UserViewMapper : Mapper<UserView, User> {

    override fun mapToView(type: User): UserView {
        return UserView(
            type.id,
            type.name,
            type.email
        )
    }
}