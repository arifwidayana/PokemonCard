package com.arifwidayana.pokemoncard.data.local.model.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: String,
    val name: String,
    val level: String,
    val hp: String,
    val types: String,
    val artist: String,
    val rarity: String,
    val flavorText: String,
    val imageSmall: Bitmap
)