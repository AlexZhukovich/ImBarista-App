package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.AuthUserEntity
import com.alexzh.imbarista.remote.model.AuthUserModel

class AuthUserMapper : ModelMapper<AuthUserModel, AuthUserEntity> {

    override fun mapFromModel(model: AuthUserModel): AuthUserEntity {
        return AuthUserEntity(
            model.name,
            model.token
        )
    }
}