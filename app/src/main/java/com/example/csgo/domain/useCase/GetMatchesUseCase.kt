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

    suspend operator fun invoke(page: Int): Flow<Resource<List<Match>>> = flow {
        emit(matchesRepository.getMatches(SORT, STATUS, PER_PAGE, page))
    }.map {
        it.let {
            Resource.success(it)
        }
    }.onStart {
        emit(Resource.loading())
    }.catch {
        emit(Resource.error(it))
    }.flowOn(dispatcher)

    companion object {
        const val STATUS = "running, not_started"
        const val SORT = "-status, begin_at"
        const val PER_PAGE = 20
    }


}