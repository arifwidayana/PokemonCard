package com.arifwidayana.pokemoncard.data.local.model.mapper

import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonEntity
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonParamEntity
import javax.inject.Inject

interface LocalPokemonMapper {
    fun mapCardPokemonToListData(pokemonEntity: List<PokemonEntity>): List<PokemonParamEntity>
    fun mapCardPokemonToData(pokemonEntity: PokemonEntity): PokemonParamEntity
}

class LocalPokemonMapperImpl @Inject constructor() : LocalPokemonMapper {
    override fun mapCardPokemonToListData(pokemonEntity: List<PokemonEntity>): List<PokemonParamEntity> {
        return pokemonEntity.map {
            mapCardPokemonToData(it)
        }
    }

    override fun mapCardPokemonToData(pokemonEntity: PokemonEntity): PokemonParamEntity {
        return PokemonParamEntity(
            id = pokemonEntity.id,
            name = pokemonEntity.name,
            level = pokemonEntity.level,
            hp = pokemonEntity.hp,
            types = pokemonEntity.types,
            artist = pokemonEntity.artist,
            rarity = pokemonEntity.rarity,
            flavorText = pokemonEntity.flavorText,
            imageSmall = pokemonEntity.imageSmall
        )
    }
}