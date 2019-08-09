package com.alexzh.imbarista.cache.mapper

import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.cache.model.User

class UserMapper : CachedMapper<User, UserEntity> {

    override fun mapFromCached(cacheModel: User): UserEntity {
        return UserEntity(
            cacheModel.id,
            cacheModel.name,
            cacheModel.email
        )
    }

    override fun mapToCached(entityModel: UserEntity): User {
        return User(
            entityModel.id,
            entityModel.name,
            entityModel.email
        )
    }

}