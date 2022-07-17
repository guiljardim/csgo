package com.example.csgo.domain.useCase

import com.example.csgo.domain.model.Match
import com.example.csgo.domain.repository.MatchesRepository
import com.example.csgo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<Resource<List<Match>>> = flow {
        emit(matchesRepository.getMatches())
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