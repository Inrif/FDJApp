package com.abbesolo.fdjapp.domain.usecase

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.data.repositories.SportsRepository
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 21/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */
class GetLeaguesUseCase @Inject constructor(private val repository: SportsRepository) {
    suspend operator fun invoke(): List<League> {
        return repository.getAllLeagues()
    }
}

class GetTeamsByLeagueUseCase @Inject constructor(private val repository: SportsRepository) {
    suspend operator fun invoke(leagueName: String): List<Team> {
        return repository.getTeamsByLeague(leagueName)
            .filterIndexed { index, _ -> index % 2 == 0 }
            .sortedByDescending { it.strTeam }
    }
}
