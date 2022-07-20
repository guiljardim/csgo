package com.example.csgo.di

import com.example.csgo.BuildConfig.BASE_URL
import com.example.csgo.BuildConfig.DEBUG
import com.example.csgo.data.api.MatchesService
import com.example.csgo.data.api.OpponentsService
import com.example.csgo.data.datasource.MatchesRemoteDataSource
import com.example.csgo.data.datasource.OpponentsRemoteDataSource
import com.example.csgo.data.repository.MatchesRepositoryImpl
import com.example.csgo.data.repository.OpponentsRepositoryImpl
import com.example.csgo.domain.repository.MatchesRepository
import com.example.csgo.domain.repository.OpponentsRepository
import com.example.csgo.domain.useCase.GetMatchesUseCase
import com.example.csgo.domain.useCase.GetOpponentsDetailsUseCase
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
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (DEBUG) {
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
    fun provideMatchesService(retrofit: Retrofit): MatchesService =
        retrofit.create(MatchesService::class.java)

    @Provides
    fun provideOpponentsService(retrofit: Retrofit): OpponentsService =
        retrofit.create(OpponentsService::class.java)

    @Provides
    fun provideMatchesRemoteDataSource(matchesService: MatchesService) =
        MatchesRemoteDataSource(matchesService)

    @Provides
    fun provideOpponentsRemoteDataSource(opponentsService: OpponentsService) =
        OpponentsRemoteDataSource(opponentsService)

    @Provides
    fun provideMatchesRepository(matchesRemoteDataSource: MatchesRemoteDataSource): MatchesRepository =
        MatchesRepositoryImpl(matchesRemoteDataSource)

    @Provides
    fun provideOpponentsRepository(opponentsRemoteDataSource: OpponentsRemoteDataSource): OpponentsRepository =
        OpponentsRepositoryImpl(opponentsRemoteDataSource)

    @Provides
    fun provideGetMatchesUseCase(matchesRepository: MatchesRepository) =
        GetMatchesUseCase(matchesRepository)

    @Provides
    fun provideGetOpponentsDetailsUseCase(opponentsRepository: OpponentsRepository) =
        GetOpponentsDetailsUseCase(opponentsRepository)


}
