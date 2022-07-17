package com.example.csgo.data.api

import com.example.csgo.data.model.MatchRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesService {

    @GET("csgo/matches")
    suspend fun getMatchesSync(
        @Query("filter[begin_at]") begin_at: String,
        @Query("token") token: String,
    ): List<MatchRemote>
}