package com.example.csgo.data.datasource

import com.example.csgo.data.MatchesService
import com.example.csgo.data.model.MatchRemote
import javax.inject.Inject

class MatchesRemoteDataSource @Inject constructor(
    private val service: MatchesService
) {
    suspend operator fun invoke(apiKey: String): List<MatchRemote> {
        return service.getMatchesSync(apiKey)
    }
}