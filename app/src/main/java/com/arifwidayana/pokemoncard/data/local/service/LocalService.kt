package com.arifwidayana.pokemoncard.data.local.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalService {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postInsertPokemon(pokemonEntity: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table")
    fun getPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_table WHERE id LIKE :id+'%'")
    fun getSearchPokemon(id: String): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    fun getDetailPokemon(id: String): Flow<PokemonEntity>
}