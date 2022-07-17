package com.example.csgo.domain.repository

import com.example.csgo.domain.model.Match.Opponent

interface OpponentsRepository {

    suspend fun getOpponents(id: Int): List<Opponent>?
}