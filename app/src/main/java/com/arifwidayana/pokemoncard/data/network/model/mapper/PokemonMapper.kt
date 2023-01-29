package com.arifwidayana.pokemoncard.data.network.model.mapper

import android.graphics.Bitmap
import com.arifwidayana.pokemoncard.common.utils.ImageUtils
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonEntity
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface PokemonMapper {
    fun mapCardPokemonToListData(cardPokemonResponse: List<CardPokemonResponse>): List<PokemonEntity>
    fun mapCardPokemonToData(cardPokemonResponse: CardPokemonResponse): PokemonEntity
}

class PokemonMapperImpl @Inject constructor() : PokemonMapper {
    override fun mapCardPokemonToListData(cardPokemonResponse: List<CardPokemonResponse>): List<PokemonEntity> {
        return cardPokemonResponse.map {
            mapCardPokemonToData(it)
        }
    }

    override fun mapCardPokemonToData(cardPokemonResponse: CardPokemonResponse): PokemonEntity {
        return PokemonEntity(
            id = cardPokemonResponse.id.orEmpty(),
            name = cardPokemonResponse.name.orEmpty(),
            level = cardPokemonResponse.level.orEmpty(),
            hp = cardPokemonResponse.hp.orEmpty(),
            types = cardPokemonResponse.types.joinToString(),
            artist = cardPokemonResponse.artist.orEmpty(),
            rarity = cardPokemonResponse.rarity.orEmpty(),
            flavorText = cardPokemonResponse.flavorText.orEmpty(),
            imageSmall = runBlocking {
                ImageUtils.getImageFromUrl(
                    url = cardPokemonResponse.images?.small.orEmpty()
                ) ?: return@runBlocking Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)
            }
        )
    }
}