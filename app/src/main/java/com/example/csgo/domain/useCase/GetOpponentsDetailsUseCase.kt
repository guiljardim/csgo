package com.example.csgo.domain.useCase

import com.example.csgo.domain.model.Match.Opponent
import com.example.csgo.domain.repository.OpponentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOpponentsDetailsUseCase @Inject constructor(
    private val opponentsRepository: OpponentsRepository,
) {
    suspend operator fun invoke(id: Int): Flow<List<Opponent>?> =
        flow { emit(opponentsRepository.getOpponents(id)) }

}