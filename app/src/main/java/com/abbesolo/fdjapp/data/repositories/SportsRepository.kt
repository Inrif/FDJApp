package com.abbesolo.fdjapp.data.repositories

import com.abbesolo.fdjapp.data.api.SportsApi
import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 21/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */
class SportsRepository @Inject constructor(private val api: SportsApi) {

    suspend fun getAllLeagues(): List<League> {
        return api.getAllLeagues().leagues
    }

    suspend fun getTeamsByLeague(leagueName: String): List<Team> {
        return api.getTeamsByLeague(leagueName).teams
    }
}
