package com.arifwidayana.pokemoncard.presentation.ui.home

import androidx.paging.PagingData
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonParamEntity
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonParamResponse
import kotlinx.coroutines.flow.StateFlow

interface HomeContract {
    val searchPokemonResult: StateFlow<Resource<PagingData<CardPokemonParamResponse>>>
    val searchLocalPokemonResult: StateFlow<Resource<List<PokemonParamEntity>>>
    fun searchPokemon(searchCardPokemon: String)
    fun searchLocalPokemon(searchCardPokemon: String)
}