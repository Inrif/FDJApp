package com.abbesolo.fdjapp.domain.usecase

import com.abbesolo.fdjapp.di.IoDispatcher
import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity
import com.abbesolo.fdjapp.domain.repository.SportsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 21/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
// */

class GetLeaguesUseCase @Inject constructor(
    private val repository: SportsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<LeagueEntity> = withContext(ioDispatcher) {
        repository.getAllLeagues()
    }
}


class GetTeamsByLeagueUseCase @Inject constructor(
    private val repository: SportsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(leagueName: String): List<TeamEntity> = withContext(ioDispatcher) {
        repository.getTeamsByLeague(leagueName)
            .filterIndexed { index, _ -> index % 2 == 0 }
            .sortedByDescending { it.name }
    }
}