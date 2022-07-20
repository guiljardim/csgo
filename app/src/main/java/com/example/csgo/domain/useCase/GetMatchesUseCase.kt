package com.example.csgo.domain.useCase

import com.example.csgo.domain.model.Match
import com.example.csgo.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


private const val STATUS = "running, not_started"
private const val SORT = "-status, begin_at"
private const val PER_PAGE = 20

class GetMatchesUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
) {
    suspend operator fun invoke(page: Int): Flow<List<Match>?> =
        flow { emit(matchesRepository.getMatches(SORT, STATUS, PER_PAGE, page)) }

}