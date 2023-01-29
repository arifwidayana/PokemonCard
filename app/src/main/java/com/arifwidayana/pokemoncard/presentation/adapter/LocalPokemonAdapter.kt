package com.arifwidayana.pokemoncard.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arifwidayana.pokemoncard.data.local.model.entity.PokemonParamEntity
import com.arifwidayana.pokemoncard.databinding.CardPokemonBinding
import com.bumptech.glide.Glide

class LocalPokemonAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<PokemonParamEntity, LocalPokemonAdapter.CardPokemonViewHolder>(
        Differ()
    ) {
    class CardPokemonViewHolder(
        private val binding: CardPokemonBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PokemonParamEntity) {
            binding.apply {
                binding.apply {
                    Glide.with(root)
                        .load(data.imageSmall)
                        .fitCenter()
                        .into(sivImage)
                    tvNamePokemon.text = data.name

                    root.setOnClickListener {
                        onClick.invoke(data.id)
                    }
                }
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<PokemonParamEntity>() {
        override fun areItemsTheSame(
            oldItem: PokemonParamEntity,
            newItem: PokemonParamEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PokemonParamEntity,
            newItem: PokemonParamEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPokemonViewHolder {
        val binding =
            CardPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardPokemonViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CardPokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}