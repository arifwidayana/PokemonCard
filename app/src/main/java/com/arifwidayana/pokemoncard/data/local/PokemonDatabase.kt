package com.arifwidayana.pokemoncard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arifwidayana.pokemoncard.common.utils.Converters
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonEntity
import com.arifwidayana.pokemoncard.data.local.service.LocalService

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun localService(): LocalService
}