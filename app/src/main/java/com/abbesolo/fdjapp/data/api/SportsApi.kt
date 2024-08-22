package com.abbesolo.fdjapp.data.api

import com.abbesolo.fdjapp.data.models.LeagueResponse
import com.abbesolo.fdjapp.data.models.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by HOUNSA ROMUALD on 21/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */
interface SportsApi {
    @GET("all_leagues.php")
    suspend fun getAllLeagues(): LeagueResponse

    @GET("search_all_teams.php")
    suspend fun getTeamsByLeague(@Query("l") leagueName: String): TeamResponse
}
