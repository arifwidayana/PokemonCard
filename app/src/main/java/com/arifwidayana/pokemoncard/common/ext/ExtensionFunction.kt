package com.arifwidayana.pokemoncard.common.ext

import android.widget.SearchView

fun SearchView.changed(
    onQueryTextSubmit: ((String) -> Unit)? = null,
    onQueryTextChange: ((String) -> Unit)? = null
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            onQueryTextSubmit?.invoke(p0.toString())
            return false
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            onQueryTextChange?.invoke(p0.toString())
            return false
        }
    })
}