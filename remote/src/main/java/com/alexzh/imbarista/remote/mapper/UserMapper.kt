package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.remote.model.UserModel

class UserMapper : ModelMapper<UserModel, UserEntity> {

    override fun mapFromModel(model: UserModel): UserEntity {
        return UserEntity(
            model.id,
            model.name,
            model.email
        )
    }
}