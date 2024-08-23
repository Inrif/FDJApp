package com.abbesolo.fdjapp.data.repository

import com.abbesolo.fdjapp.data.api.SportsApi
import com.abbesolo.fdjapp.data.mappers.toLeagueEntity
import com.abbesolo.fdjapp.data.mappers.toTeamEntity
import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity
import com.abbesolo.fdjapp.domain.repository.SportsRepository
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 21/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
// */

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi
) : SportsRepository {

    override suspend fun getAllLeagues(): List<LeagueEntity> {
        return api.getAllLeagues().leagues.map { it.toLeagueEntity() }
    }

    override suspend fun getTeamsByLeague(leagueName: String): List<TeamEntity> {
        return api.getTeamsByLeague(leagueName).teams.map { it.toTeamEntity() }
    }
}