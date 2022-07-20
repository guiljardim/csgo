package com.example.csgo.data.repository

import com.example.csgo.data.datasource.OpponentsRemoteDataSource
import com.example.csgo.data.mapper.mapToOpponent
import com.example.csgo.domain.model.Match
import com.example.csgo.domain.repository.OpponentsRepository
import javax.inject.Inject

class OpponentsRepositoryImpl @Inject constructor(private val opponentsRemoteDataSource: OpponentsRemoteDataSource) :
    OpponentsRepository {
    override suspend fun getOpponents(id: Int): List<Match.Opponent>? {
        return opponentsRemoteDataSource.invoke(id)
            ?.mapToOpponent()
    }
}