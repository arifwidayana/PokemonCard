package com.arifwidayana.pokemoncard.data.repository

import com.arifwidayana.pokemoncard.common.base.BaseRepository
import com.arifwidayana.pokemoncard.common.base.BaseResponse
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.network.datasource.DetailCardDatasource
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface DetailCardRepository {
    suspend fun getDetailPokemon(id: String): Flow<Resource<BaseResponse<CardPokemonResponse>>>
}

class DetailCardRepositoryImpl @Inject constructor(
    private val detailCardDatasource: DetailCardDatasource
): DetailCardRepository, BaseRepository() {
    override suspend fun getDetailPokemon(id: String): Flow<Resource<BaseResponse<CardPokemonResponse>>> = flow {
        emit(proceed { detailCardDatasource.getDetailPokemon(id) })
    }
}