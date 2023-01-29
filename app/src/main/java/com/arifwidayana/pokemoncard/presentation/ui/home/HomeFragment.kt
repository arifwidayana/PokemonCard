package com.arifwidayana.pokemoncard.presentation.ui.home

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.arifwidayana.pokemoncard.common.base.BaseFragment
import com.arifwidayana.pokemoncard.common.ext.changed
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonParamEntity
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonParamResponse
import com.arifwidayana.pokemoncard.databinding.FragmentHomeBinding
import com.arifwidayana.pokemoncard.presentation.adapter.LocalPokemonAdapter
import com.arifwidayana.pokemoncard.presentation.adapter.NetworkPokemonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override fun initView() {
        onView()
        onClick()
    }

    private fun onView() {
        viewModel.apply {
            searchPokemon("")
        }
    }

    private fun onClick() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.searchPokemon("")
                swipeRefreshLayout.isRefreshing = false
            }
            svPokemon.changed(onQueryTextSubmit = {
                viewModel.searchPokemon(it)
            })
        }
    }

    override fun observeData() {
        lifecycleScope.apply {
            viewModel.searchPokemonResult.asLiveData().observe(viewLifecycleOwner) {
                if(it is Resource.Success) {
                    setStateNetworkCard(it.data)
                    Log.d("TAG", "observeData: ${it.data?.toString()}")
                } else if (it is Resource.Error) {
                    Log.d("TAG", "observeData: ${it.exception}")
                }
            }

            viewModel.searchLocalPokemonResult.asLiveData().observe(viewLifecycleOwner) {
                if(it is Resource.Success) {
                    setStateLocalCard(it.data)
                    Log.d("TAG", "observeData: ${it.data?.toString()}")
                } else if (it is Resource.Error) {
                    Log.d("TAG", "observeData: ${it.exception}")
                }
            }
        }
    }

    private fun setStateLocalCard(data: List<PokemonParamEntity>?) {
        binding.apply {
            data?.let {
                val adapter = LocalPokemonAdapter{
                    moveNav(
                        HomeFragmentDirections.actionHomeFragmentToDetailCardFragment(it)
                    )
                }
                adapter.submitList(data)
                rvCardPokemon.adapter = adapter
            }
        }
    }

    private fun setStateNetworkCard(data: PagingData<CardPokemonParamResponse>?) {
        binding.apply {
            data?.let {
                val adapter = NetworkPokemonAdapter{
                    moveNav(
                        HomeFragmentDirections.actionHomeFragmentToDetailCardFragment(it)
                    )
                }
                adapter.submitData(lifecycle, data)
                adapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner) {
                    when(it.source.refresh) {
                        is LoadState.Loading -> {
                        }
                        is LoadState.Error -> {
                            viewModel.searchLocalPokemon("")
                        }
                        else -> {
                        }
                    }
                }
                rvCardPokemon.adapter = adapter
            }
        }
    }
}