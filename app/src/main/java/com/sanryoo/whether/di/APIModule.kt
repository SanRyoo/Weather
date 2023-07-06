package com.sanryoo.whether.di

import com.sanryoo.whether.BuildConfig
import com.sanryoo.whether.feature.data.api.WeatherAPI
import com.sanryoo.whether.feature.data.repository.WeatherRepository
import com.sanryoo.whether.feature.domain.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object APIModule {

    @Provides
    @ViewModelScoped
    fun provideWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(weatherAPI)
    }

}