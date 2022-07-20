package com.example.csgo.data.datasource

import com.example.csgo.BuildConfig
import com.example.csgo.data.api.OpponentsService
import com.example.csgo.data.model.OpponentsDetailsRemote
import retrofit2.Response
import javax.inject.Inject

class OpponentsRemoteDataSource @Inject constructor(
    private val service: OpponentsService
) {
    suspend operator fun invoke(id: Int): OpponentsDetailsRemote? {
        return service.getOpponentsSync(id, BuildConfig.API_KEY).body()
    }
}