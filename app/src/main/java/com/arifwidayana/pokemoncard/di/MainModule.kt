package com.arifwidayana.pokemoncard.di

import android.content.Context
import androidx.room.Room
import com.arifwidayana.pokemoncard.common.base.BaseGenericViewModel
import com.arifwidayana.pokemoncard.common.utils.Constant
import com.arifwidayana.pokemoncard.data.local.PokemonDatabase
import com.arifwidayana.pokemoncard.data.local.datasource.LocalDatasource
import com.arifwidayana.pokemoncard.data.local.datasource.LocalDatasourceImpl
import com.arifwidayana.pokemoncard.data.local.model.mapper.LocalPokemonMapper
import com.arifwidayana.pokemoncard.data.local.model.mapper.LocalPokemonMapperImpl
import com.arifwidayana.pokemoncard.data.local.service.LocalService
import com.arifwidayana.pokemoncard.data.network.datasource.DetailCardDatasource
import com.arifwidayana.pokemoncard.data.network.datasource.DetailCardDatasourceImpl
import com.arifwidayana.pokemoncard.data.network.datasource.HomeDatasource
import com.arifwidayana.pokemoncard.data.network.datasource.HomeDatasourceImpl
import com.arifwidayana.pokemoncard.data.network.model.mapper.CardPokemonMapper
import com.arifwidayana.pokemoncard.data.network.model.mapper.CardPokemonMapperImpl
import com.arifwidayana.pokemoncard.data.network.model.mapper.PokemonMapper
import com.arifwidayana.pokemoncard.data.network.model.mapper.PokemonMapperImpl
import com.arifwidayana.pokemoncard.data.network.service.PokemonService
import com.arifwidayana.pokemoncard.data.repository.*
import com.arifwidayana.pokemoncard.presentation.ui.detail.DetailCardViewModel
import com.arifwidayana.pokemoncard.presentation.ui.home.HomeViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object MainModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object LocalModule {
        @Provides
        @Singleton
        fun providePokemonDatabase(
            @ApplicationContext context: Context
        ): PokemonDatabase = Room
            .databaseBuilder(
                context,
                PokemonDatabase::class.java,
                Constant.DB_NAME
            )
            .build()

        @Provides
        @Singleton
        fun providePokemonService(pokemonDatabase: PokemonDatabase) = pokemonDatabase.localService()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {
        @Provides
        @Singleton
        fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
            return ChuckerInterceptor.Builder(context).build()
        }

        @Provides
        @Singleton
        fun providePokemonService(chuckerInterceptor: ChuckerInterceptor): PokemonService {
            return PokemonService.invoke(chuckerInterceptor)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object DataSourceModule {
        @Provides
        @Singleton
        fun provideLocalPokemonMapper(): LocalPokemonMapper {
            return LocalPokemonMapperImpl()
        }

        @Provides
        @Singleton
        fun provideLocalCardPokemonMapper(): PokemonMapper {
            return PokemonMapperImpl()
        }

        @Provides
        @Singleton
        fun provideCardPokemonMapper(): CardPokemonMapper {
            return CardPokemonMapperImpl()
        }

        @Provides
        @Singleton
        fun provideLocalDatasource(
            localService: LocalService
        ): LocalDatasource {
            return LocalDatasourceImpl(localService)
        }

        @Provides
        @Singleton
        fun provideHomeDatasource(
            pokemonService: PokemonService
        ): HomeDatasource {
            return HomeDatasourceImpl(pokemonService)
        }

        @Provides
        @Singleton
        fun provideDetailCardDatasource(pokemonService: PokemonService): DetailCardDatasource {
            return DetailCardDatasourceImpl(pokemonService)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        @Provides
        @Singleton
        fun provideLocalRepository(localDatasource: LocalDatasource, localPokemonMapper: LocalPokemonMapper): LocalRepository {
            return LocalRepositoryImpl(localDatasource, localPokemonMapper)
        }

        @Provides
        @Singleton
        fun provideHomeRepository(homeDatasource: HomeDatasource, cardPokemonMapper: CardPokemonMapper): HomeRepository {
            return HomeRepositoryImpl(homeDatasource, cardPokemonMapper)
        }

        @Provides
        @Singleton
        fun provideDetailCardRepository(detailCardDatasource: DetailCardDatasource): DetailCardRepository {
            return DetailCardRepositoryImpl(detailCardDatasource)
        }
    }

    @Module
    @InstallIn(FragmentComponent::class)
    object ViewModelModule {
        @Provides
        @FragmentScoped
        fun provideHomeViewModel(
            localRepository: LocalRepository,
            homeRepository: HomeRepository,
            pokemonMapper: PokemonMapper
        ): HomeViewModel {
            return BaseGenericViewModel(HomeViewModel(localRepository, homeRepository, pokemonMapper)).create(
                HomeViewModel::class.java
            )
        }

        @Provides
        @FragmentScoped
        fun provideDetailCardViewModel(detailCardRepository: DetailCardRepository): DetailCardViewModel {
            return BaseGenericViewModel(DetailCardViewModel(detailCardRepository)).create(
                DetailCardViewModel::class.java
            )
        }
    }
}