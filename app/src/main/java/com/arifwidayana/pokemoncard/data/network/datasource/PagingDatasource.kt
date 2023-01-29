package com.arifwidayana.pokemoncard.data.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arifwidayana.pokemoncard.common.utils.Constant.DEFAULT_INDEX_PAGE
import com.arifwidayana.pokemoncard.common.utils.Constant.DEFAULT_PAGE_SIZE
import com.arifwidayana.pokemoncard.common.utils.Constant.DEFAULT_SEARCH
import com.arifwidayana.pokemoncard.data.network.model.request.CardPokemonRequest
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import com.arifwidayana.pokemoncard.data.network.service.PokemonService
import retrofit2.HttpException
import java.io.IOException

class PagingDatasource(
    private val pokemonService: PokemonService,
    private val cardPokemonRequest: CardPokemonRequest
): PagingSource<Int, CardPokemonResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CardPokemonResponse> {
        return try {
            val pageIndex = params.key ?: DEFAULT_INDEX_PAGE
            val response = if (cardPokemonRequest.searchCard.isEmpty()) {
                pokemonService.searchCard(
                    nameSearch = cardPokemonRequest.searchCard,
                    page = pageIndex,
                    pageSize = DEFAULT_PAGE_SIZE
                )
            } else {
                pokemonService.searchCard(
                    nameSearch = DEFAULT_SEARCH+cardPokemonRequest.searchCard+"*",
                    page = pageIndex,
                    pageSize = DEFAULT_PAGE_SIZE
                )
            }
            LoadResult.Page(
                data = response.data,
                prevKey = if (pageIndex == DEFAULT_INDEX_PAGE) null else pageIndex - 1,
                nextKey = if (response.data.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CardPokemonResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}