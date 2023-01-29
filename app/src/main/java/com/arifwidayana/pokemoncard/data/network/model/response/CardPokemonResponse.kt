package com.arifwidayana.pokemoncard.data.network.model.response

import com.google.gson.annotations.SerializedName

data class CardPokemonResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("hp")
    val hp: String?,
    @SerializedName("types")
    val types: List<String>,
    @SerializedName("artist")
    val artist: String?,
    @SerializedName("rarity")
    val rarity: String?,
    @SerializedName("flavorText")
    val flavorText: String?,
    @SerializedName("images")
    val images: Images?,
) {
    data class Images(
        @SerializedName("large")
        val large: String?,
        @SerializedName("small")
        val small: String?
    )
}