package com.example.csgo.data.api

import com.example.csgo.data.model.OpponentsRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpponentsService {

    @GET("matches/{match_id_or_slug}/opponents")
    suspend fun getMatchesSync(
        @Path("match_id_or_slug") match_id_or_slug: Int,
        @Query("token") token: String
    ): OpponentsRemote
}