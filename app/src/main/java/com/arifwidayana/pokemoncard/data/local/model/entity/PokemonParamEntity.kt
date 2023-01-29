package com.arifwidayana.pokemoncard.data.local.model.entity

import android.graphics.Bitmap

data class PokemonParamEntity(
    val id: String,
    val name: String,
    val level: String,
    val hp: String,
    val types: String,
    val artist: String,
    val rarity: String,
    val flavorText: String,
    val imageSmall: Bitmap
)