package com.abbesolo.fdjapp.domain.repository

import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity

interface SportsRepository {
    suspend fun getAllLeagues(): List<LeagueEntity>
    suspend fun getTeamsByLeague(leagueName: String): List<TeamEntity>
}