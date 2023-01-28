package com.arifwidayana.pokemoncard.common.base

import com.arifwidayana.pokemoncard.common.wrapper.Resource

abstract class BaseRepository {
    suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}