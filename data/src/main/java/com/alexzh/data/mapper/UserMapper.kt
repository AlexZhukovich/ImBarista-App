package com.alexzh.data.mapper

import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.domain.model.User

class UserMapper : EntityMapper<UserEntity, User> {
    override fun mapFromEntity(entity: UserEntity): User {
        return User(
            entity.id,
            entity.name,
            entity.email
        )
    }

    override fun mapToEntity(domainModel: User): UserEntity {
        return UserEntity(
            domainModel.id,
            domainModel.name,
            domainModel.email
        )
    }
}