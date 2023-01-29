package com.arifwidayana.pokemoncard.data.network.datasource

import com.arifwidayana.pokemoncard.common.base.BaseResponse
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import com.arifwidayana.pokemoncard.data.network.service.PokemonService
import javax.inject.Inject

interface DetailCardDatasource {
    suspend fun getDetailPokemon(id: String): BaseResponse<CardPokemonResponse>
}

class DetailCardDatasourceImpl @Inject constructor(
    private val pokemonService: PokemonService
) : DetailCardDatasource {
    override suspend fun getDetailPokemon(id: String): BaseResponse<CardPokemonResponse> {
        return pokemonService.detailCard(id)
    }
}