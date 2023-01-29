package com.arifwidayana.pokemoncard.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonParamResponse
import com.arifwidayana.pokemoncard.databinding.CardPokemonBinding
import com.bumptech.glide.Glide

class NetworkPokemonAdapter(
    private val onClick: (String) -> Unit,
) : PagingDataAdapter<CardPokemonParamResponse, NetworkPokemonAdapter.CardPokemonViewHolder>(DIFF_CALLBACK) {

    inner class CardPokemonViewHolder(
        private val binding: CardPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CardPokemonParamResponse) {
            binding.apply {
                Glide.with(root)
                    .asBitmap()
                    .load(data.images.small)
                    .fitCenter()
                    .into(sivImage)
                tvNamePokemon.text = data.name

                root.setOnClickListener {
                    onClick.invoke(data.id)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CardPokemonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPokemonViewHolder {
        val binding = CardPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardPokemonViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CardPokemonParamResponse>() {
            override fun areItemsTheSame(oldItem: CardPokemonParamResponse, newItem: CardPokemonParamResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CardPokemonParamResponse, newItem: CardPokemonParamResponse): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}