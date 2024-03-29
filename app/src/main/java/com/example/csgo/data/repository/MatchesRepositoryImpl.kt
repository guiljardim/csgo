package com.example.csgo.data.repository

import com.example.csgo.data.datasource.MatchesRemoteDataSource
import com.example.csgo.data.mapper.mapToMatch
import com.example.csgo.domain.model.Match
import com.example.csgo.domain.repository.MatchesRepository
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(private val matchesRemoteDataSource: MatchesRemoteDataSource) :
    MatchesRepository {

    override suspend fun getMatches(
        sort: String,
        status: String,
        per_page: Int,
        page: Int
    ): List<Match> {
        val result = matchesRemoteDataSource.invoke(sort, status, per_page, page)
        return result.first.mapToMatch(result.second)

    }

}