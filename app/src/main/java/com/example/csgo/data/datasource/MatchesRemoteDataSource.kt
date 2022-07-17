package com.example.csgo.data.datasource

import android.text.format.DateFormat
import com.example.csgo.data.api.MatchesService
import com.example.csgo.data.model.MatchRemote
import java.util.*
import javax.inject.Inject


class MatchesRemoteDataSource @Inject constructor(
    private val service: MatchesService
) {
    suspend operator fun invoke(apiKey: String): List<MatchRemote> {
        return service.getMatchesSync(
            "-status",
            DateFormat.format("yyyy-MM-dd", Date()).toString(),
            false,
            apiKey,
        )
    }
}