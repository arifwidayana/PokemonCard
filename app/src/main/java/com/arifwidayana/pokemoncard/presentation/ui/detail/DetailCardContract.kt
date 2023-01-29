package com.arifwidayana.pokemoncard.presentation.ui.detail

import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import kotlinx.coroutines.flow.StateFlow

interface DetailCardContract {
    val detailPokemonResult: StateFlow<Resource<CardPokemonResponse>>
    fun detailPokemon(id: String)
}