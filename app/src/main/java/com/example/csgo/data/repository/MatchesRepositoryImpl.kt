package com.example.csgo.data.repository

import com.example.csgo.BuildConfig.API_KEY
import com.example.csgo.data.datasource.MatchesRemoteDataSource
import com.example.csgo.data.mapper.mapToMatch
import com.example.csgo.domain.model.Match
import com.example.csgo.domain.repository.MatchesRepository
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(private val matchesRemoteDataSource: MatchesRemoteDataSource) :
    MatchesRepository {

    override suspend fun getMatches(): List<Match> {
        return matchesRemoteDataSource.invoke(API_KEY).mapToMatch()

    }

}