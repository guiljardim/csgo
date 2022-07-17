package com.example.csgo.data.repository

import com.example.csgo.BuildConfig
import com.example.csgo.data.datasource.MatchesRemoteDataSource
import com.example.csgo.data.mapper.mapToMatch
import com.example.csgo.domain.repository.MatchesRepository
import com.example.csgo.domain.model.Match
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(private val matchesRemoteDataSource: MatchesRemoteDataSource) :
    MatchesRepository {

    override suspend fun getMatches(): List<Match> =
        matchesRemoteDataSource.invoke(BuildConfig.API_KEY).mapToMatch()

}