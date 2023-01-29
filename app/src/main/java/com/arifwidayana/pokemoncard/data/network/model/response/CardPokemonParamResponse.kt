package com.arifwidayana.pokemoncard.data.network.model.response

data class CardPokemonParamResponse(
    val id: String,
    val name: String,
    val level: String,
    val hp: String,
    val types: List<String>,
    val artist: String,
    val rarity: String,
    val flavorText: String,
    val images: Images,
) {
    data class Images(
        val large: String,
        val small: String
    )
}