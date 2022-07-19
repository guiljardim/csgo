package com.example.csgo.data.api

import com.example.csgo.data.model.MatchRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesService {

    @GET("csgo/matches")
    suspend fun getMatchesSync(
        @Query("sort") sort: String,
        @Query("filter[status]") status: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Response<List<MatchRemote>>
}