package com.arifwidayana.pokemoncard.data.network.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arifwidayana.pokemoncard.common.base.BaseResponse
import com.arifwidayana.pokemoncard.common.utils.Constant
import com.arifwidayana.pokemoncard.common.utils.Constant.NETWORK_PAGE_SIZE
import com.arifwidayana.pokemoncard.data.network.model.request.CardPokemonRequest
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import com.arifwidayana.pokemoncard.data.network.service.PokemonService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HomeDatasource {
    suspend fun searchCard(cardPokemonRequest: CardPokemonRequest): Flow<PagingData<CardPokemonResponse>>
    suspend fun searchToLocalCard(): BaseResponse<List<CardPokemonResponse>>
}

class HomeDatasourceImpl @Inject constructor(
    private val pokemonService: PokemonService
) : HomeDatasource {
    override suspend fun searchCard(cardPokemonRequest: CardPokemonRequest): Flow<PagingData<CardPokemonResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingDatasource(
                    pokemonService = pokemonService,
                    cardPokemonRequest = cardPokemonRequest
                )
            }
        ).flow
    }

    override suspend fun searchToLocalCard(): BaseResponse<List<CardPokemonResponse>> {
        return pokemonService.searchCard(
            nameSearch = "",
            page = 1,
            pageSize = Constant.DEFAULT_PAGE_SIZE
        )
    }
}