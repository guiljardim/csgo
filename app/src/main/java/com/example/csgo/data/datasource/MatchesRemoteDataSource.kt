package com.example.csgo.data.datasource

import com.example.csgo.BuildConfig
import com.example.csgo.data.api.MatchesService
import com.example.csgo.data.model.MatchRemote
import javax.inject.Inject


private const val defaultValue = 0
private const val headerTotal = "X-Total"

class MatchesRemoteDataSource @Inject constructor(
    private val service: MatchesService
) {
    suspend operator fun invoke(
        sort: String,
        status: String,
        per_page: Int,
        page: Int
    ): Pair<List<MatchRemote>?, Int> {
        val result = this.service.getMatchesSync(
            sort,
            status,
            per_page,
            page,
            BuildConfig.API_KEY,
        )
        return Pair(result.body(), result.headers()[headerTotal]?.toInt() ?: defaultValue)
    }
}