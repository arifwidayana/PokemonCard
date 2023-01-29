package com.arifwidayana.pokemoncard.data.network.service

import com.arifwidayana.pokemoncard.BuildConfig
import com.arifwidayana.pokemoncard.common.base.BaseResponse
import com.arifwidayana.pokemoncard.common.utils.Constant.ID_PATH
import com.arifwidayana.pokemoncard.common.utils.Constant.PAGE
import com.arifwidayana.pokemoncard.common.utils.Constant.PAGE_SIZE
import com.arifwidayana.pokemoncard.common.utils.Constant.QUERY_SEARCH
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface PokemonService {
    @GET(BuildConfig.END_POINT_SEARCH_CARD)
    suspend fun searchCard(
        @Query(QUERY_SEARCH) nameSearch: String,
        @Query(PAGE) page: Int,
        @Query(PAGE_SIZE) pageSize: Int
    ): BaseResponse<List<CardPokemonResponse>>

    @GET(BuildConfig.END_POINT_DETAIL_CARD)
    suspend fun detailCard(
        @Path(ID_PATH) idCard: String
    ): BaseResponse<CardPokemonResponse>

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor : ChuckerInterceptor): PokemonService{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(PokemonService::class.java)
        }
    }
}