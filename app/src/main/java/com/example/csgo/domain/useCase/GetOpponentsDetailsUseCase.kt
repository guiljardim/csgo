package com.example.csgo.domain.useCase

import com.example.csgo.domain.model.Match.Opponent
import com.example.csgo.domain.repository.OpponentsRepository
import com.example.csgo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetOpponentsDetailsUseCase @Inject constructor(
    private val opponentsRepository: OpponentsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(id: Int): Flow<Resource<List<Opponent>>> = flow {
        emit(opponentsRepository.getOpponents(id))
    }.map {
        it.let {
            Resource.success(it)
        }
    }.onStart {
        emit(Resource.loading())
    }.catch {
        emit(Resource.error(it))
    }.flowOn(dispatcher)
}