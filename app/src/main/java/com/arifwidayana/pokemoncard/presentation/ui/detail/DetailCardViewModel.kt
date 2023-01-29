package com.arifwidayana.pokemoncard.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import com.arifwidayana.pokemoncard.data.repository.DetailCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCardViewModel @Inject constructor(
    private val detailCardRepository: DetailCardRepository
): DetailCardContract, ViewModel() {
    private val _detailPokemonResult = MutableStateFlow<Resource<CardPokemonResponse>>(Resource.Empty())
    override val detailPokemonResult: StateFlow<Resource<CardPokemonResponse>> = _detailPokemonResult

    override fun detailPokemon(id: String) {
        viewModelScope.launch {
            try {
                detailCardRepository.getDetailPokemon(id).collect {
                    _detailPokemonResult.value = Resource.Success(it.data?.data)
                }
            } catch (e: Exception) {
                _detailPokemonResult.value = Resource.Error(e)
            }
        }
    }
}