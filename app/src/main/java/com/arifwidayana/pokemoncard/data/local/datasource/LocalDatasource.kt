package com.arifwidayana.pokemoncard.data.local.datasource

import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonEntity
import com.arifwidayana.pokemoncard.data.local.service.LocalService
import com.arifwidayana.pokemoncard.data.network.model.request.CardPokemonRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDatasource {
    suspend fun postInsertPokemon(pokemonEntity: List<PokemonEntity>)
    suspend fun searchLocalCard(cardPokemonRequest: CardPokemonRequest): Flow<List<PokemonEntity>>
    suspend fun getDetailPokemon(id: String): Flow<PokemonEntity>
}

class LocalDatasourceImpl @Inject constructor(
    private val localService: LocalService
): LocalDatasource {
    override suspend fun postInsertPokemon(pokemonEntity: List<PokemonEntity>) {
        localService.postInsertPokemon(pokemonEntity)
    }

    override suspend fun searchLocalCard(cardPokemonRequest: CardPokemonRequest): Flow<List<PokemonEntity>> {
        return if(cardPokemonRequest.searchCard.isEmpty()){
            localService.getPokemon()
        } else {
            localService.getSearchPokemon(cardPokemonRequest.searchCard)
        }
    }

    override suspend fun getDetailPokemon(id: String): Flow<PokemonEntity> {
        return localService.getDetailPokemon(id)
    }
}