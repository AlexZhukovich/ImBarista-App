package com.alexzh.imbarista.cache.mapper

interface CachedMapper<C, E> {

    fun mapFromCached(cacheModel: C): E

    fun mapToCached(entityModel: E): C
}