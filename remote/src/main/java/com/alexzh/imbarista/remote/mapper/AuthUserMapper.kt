package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.AuthUserEntity
import com.alexzh.imbarista.remote.model.AuthUserModel

class AuthUserMapper : ModelMapper<AuthUserModel, AuthUserEntity> {

    override fun mapFromModel(model: AuthUserModel): AuthUserEntity {
        return AuthUserEntity(
            model.id,
            model.name,
            model.token
        )
    }
}