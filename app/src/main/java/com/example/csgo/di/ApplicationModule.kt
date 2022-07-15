package com.example.csgo.di

import com.example.csgo.BuildConfig
import com.example.csgo.data.api.MatchesService
import com.example.csgo.data.api.OpponentsService
import com.example.csgo.data.datasource.MatchesRemoteDataSource
import com.example.csgo.data.datasource.OpponentsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMatchesService(retrofit: Retrofit): MatchesService =
        retrofit.create(MatchesService::class.java)

    @Provides
    @Singleton
    fun provideOpponentsService(retrofit: Retrofit): OpponentsService =
        retrofit.create(OpponentsService::class.java)

    @Provides
    @Singleton
    fun provideMatchesRemoteDataSource(matchesService: MatchesService) =
        MatchesRemoteDataSource(matchesService)

    @Provides
    @Singleton
    fun provideOpponentsRemoteDataSource(opponentsService: OpponentsService) =
        OpponentsRemoteDataSource(opponentsService)

}
