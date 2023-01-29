package com.arifwidayana.pokemoncard.data.network.model.mapper

import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonParamResponse
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import javax.inject.Inject

interface CardPokemonMapper {
    fun mapCardPokemonToListData(cardPokemonResponse: List<CardPokemonResponse>): List<CardPokemonParamResponse>
    fun mapCardPokemonToData(cardPokemonResponse: CardPokemonResponse): CardPokemonParamResponse
}

class CardPokemonMapperImpl @Inject constructor() : CardPokemonMapper {
    override fun mapCardPokemonToListData(cardPokemonResponse: List<CardPokemonResponse>): List<CardPokemonParamResponse> {
        return cardPokemonResponse.map {
            mapCardPokemonToData(it)
        }
    }

    override fun mapCardPokemonToData(cardPokemonResponse: CardPokemonResponse): CardPokemonParamResponse {
        return CardPokemonParamResponse(
            id = cardPokemonResponse.id.orEmpty(),
            name = cardPokemonResponse.name.orEmpty(),
            level = cardPokemonResponse.level.orEmpty(),
            hp = cardPokemonResponse.hp.orEmpty(),
            types = cardPokemonResponse.types,
            artist = cardPokemonResponse.artist.orEmpty(),
            rarity = cardPokemonResponse.rarity.orEmpty(),
            flavorText = cardPokemonResponse.flavorText.orEmpty(),
            images = CardPokemonParamResponse.Images(
                small = cardPokemonResponse.images?.small.orEmpty(),
                large = cardPokemonResponse.images?.large.orEmpty()
            )
        )
    }
}