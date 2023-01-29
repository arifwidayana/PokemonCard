package com.arifwidayana.pokemoncard.data.repository

import com.arifwidayana.pokemoncard.common.base.BaseRepository
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.local.datasource.LocalDatasource
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonEntity
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonParamEntity
import com.arifwidayana.pokemoncard.data.local.model.mapper.LocalPokemonMapper
import com.arifwidayana.pokemoncard.data.network.model.request.CardPokemonRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LocalRepository {
    suspend fun postInsertPokemon(pokemonEntity: List<PokemonEntity>): Flow<Resource<Unit>>
    suspend fun getDetailPokemon(id: String): Flow<Resource<PokemonEntity>>
    suspend fun searchLocalCard(cardPokemonRequest: CardPokemonRequest): Flow<Resource<List<PokemonParamEntity>>>
}

class LocalRepositoryImpl @Inject constructor(
    private val localDatasource: LocalDatasource,
    private val localPokemonMapper: LocalPokemonMapper
) : LocalRepository, BaseRepository() {
    override suspend fun postInsertPokemon(pokemonEntity: List<PokemonEntity>): Flow<Resource<Unit>> = flow {
        emit(proceed { localDatasource.postInsertPokemon(pokemonEntity) })
    }

    override suspend fun getDetailPokemon(id: String): Flow<Resource<PokemonEntity>> = flow {
        emit(proceed { localDatasource.getDetailPokemon(id).first() })
    }

    override suspend fun searchLocalCard(cardPokemonRequest: CardPokemonRequest): Flow<Resource<List<PokemonParamEntity>>> =
        flow { emit(proceed { localDatasource.searchLocalCard(cardPokemonRequest).map {
            localPokemonMapper.mapCardPokemonToListData(it)
        }.first() }) }
}