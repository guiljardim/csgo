package com.example.csgo.data.datasource

import com.example.csgo.data.api.OpponentsService
import com.example.csgo.data.model.OpponentsDetailsRemote
import javax.inject.Inject

class OpponentsRemoteDataSource @Inject constructor(
    private val service: OpponentsService
) {
    suspend operator fun invoke(apiKey: String, id: Int): OpponentsDetailsRemote {
        return service.getOpponentsSync(id, apiKey)
    }
}