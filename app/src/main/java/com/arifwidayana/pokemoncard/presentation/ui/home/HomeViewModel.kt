package com.arifwidayana.pokemoncard.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonParamEntity
import com.arifwidayana.pokemoncard.data.network.model.mapper.PokemonMapper
import com.arifwidayana.pokemoncard.data.network.model.request.CardPokemonRequest
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonParamResponse
import com.arifwidayana.pokemoncard.data.repository.HomeRepository
import com.arifwidayana.pokemoncard.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val homeRepository: HomeRepository,
    private val pokemonMapper: PokemonMapper
) : HomeContract, ViewModel() {
    private val _searchPokemonResult =
        MutableStateFlow<Resource<PagingData<CardPokemonParamResponse>>>(Resource.Empty())
    private val _searchLocalPokemonResult =
        MutableStateFlow<Resource<List<PokemonParamEntity>>>(Resource.Empty())
    override val searchPokemonResult: StateFlow<Resource<PagingData<CardPokemonParamResponse>>> =
        _searchPokemonResult
    override val searchLocalPokemonResult: StateFlow<Resource<List<PokemonParamEntity>>> =
        _searchLocalPokemonResult

    override fun searchPokemon(searchCardPokemon: String) {
        _searchPokemonResult.value = Resource.Loading()
        viewModelScope.launch {
            homeRepository.searchCard(CardPokemonRequest(searchCard = searchCardPokemon)).collect {
                try {
                    _searchPokemonResult.value = Resource.Success(it)
                } catch (e: Exception) {
                    _searchPokemonResult.value = Resource.Error(
                        exception = e,
                        message = "Something went wrong"
                    )
                }
            }
        }
        viewModelScope.launch {
            homeRepository.searchLocalCard().collect {
                it.data?.data?.let { result ->
                    localRepository.postInsertPokemon(pokemonMapper.mapCardPokemonToListData(result)).first()
                }
            }
        }
    }

    override fun searchLocalPokemon(searchCardPokemon: String) {
        _searchLocalPokemonResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.searchLocalCard(CardPokemonRequest(searchCard = searchCardPokemon))
                .collect {
                    try {
                        _searchLocalPokemonResult.value = Resource.Success(it.data)
                    } catch (e: Exception) {
                        _searchLocalPokemonResult.value = Resource.Error(
                            exception = e,
                            message = "Something went wrong"
                        )
                    }
                }
        }
    }
}