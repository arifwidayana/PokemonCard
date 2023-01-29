package com.arifwidayana.pokemoncard.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.arifwidayana.pokemoncard.common.base.BaseRepository
import com.arifwidayana.pokemoncard.common.base.BaseResponse
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.network.datasource.HomeDatasource
import com.arifwidayana.pokemoncard.data.network.model.mapper.CardPokemonMapper
import com.arifwidayana.pokemoncard.data.network.model.request.CardPokemonRequest
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonParamResponse
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

typealias SearchCardResponse = PagingData<CardPokemonParamResponse>
typealias SearchLocalCardResponse = Resource<BaseResponse<List<CardPokemonResponse>>>

interface HomeRepository {
    suspend fun searchCard(cardPokemonRequest: CardPokemonRequest): Flow<SearchCardResponse>
    suspend fun searchLocalCard(): Flow<SearchLocalCardResponse>
}

class HomeRepositoryImpl @Inject constructor(
    private val homeDatasource: HomeDatasource,
    private val pokemonMapper: CardPokemonMapper
): HomeRepository, BaseRepository() {
    override suspend fun searchCard(cardPokemonRequest: CardPokemonRequest): Flow<SearchCardResponse> {
        return homeDatasource.searchCard(cardPokemonRequest).map {
            it.map { source ->
                pokemonMapper.mapCardPokemonToData(source)
            }
        }
    }

    override suspend fun searchLocalCard(): Flow<SearchLocalCardResponse> = flow {
        emit(proceed { homeDatasource.searchToLocalCard() })
    }
}