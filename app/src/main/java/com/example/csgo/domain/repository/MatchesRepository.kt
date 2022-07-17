package com.example.csgo.domain.repository

import com.example.csgo.domain.model.Match

interface MatchesRepository {

    suspend fun getMatches(): List<Match>
}