package com.example.csgo.data.datasource

import com.example.csgo.data.api.MatchesService
import com.example.csgo.data.model.MatchRemote
import retrofit2.Response
import javax.inject.Inject


class MatchesRemoteDataSource @Inject constructor(
    private val service: MatchesService
) {
    suspend operator fun invoke(
        apiKey: String,
        sort: String,
        status: String,
        per_page: Int,
        page: Int
    ): Response<List<MatchRemote>> {
        return this.service.getMatchesSync(
            sort,
            status,
            per_page,
            page,
            apiKey,
        )
    }
}