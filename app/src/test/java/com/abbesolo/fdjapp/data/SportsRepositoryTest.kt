package com.abbesolo.fdjapp.data

/**
 * Created by HOUNSA ROMUALD on 22/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */

import com.abbesolo.fdjapp.data.api.SportsApi
import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.LeagueResponse
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.data.models.TeamResponse
import com.abbesolo.fdjapp.data.repositories.SportsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SportsRepositoryTest {

    @Mock
    private lateinit var api: SportsApi

    private lateinit var repository: SportsRepository

    @Before
    fun setUp() {
        repository = SportsRepository(api)
    }

    @Test
    fun `getAllLeagues returns list of leagues`() = runTest {
        // Arrange
        val expectedLeagues = listOf(
            League("1", "Premier League", "Soccer", "EPL", "logo_url"),
            League("2", "La Liga", "Soccer", "LL", "logo_url")
        )
        `when`(api.getAllLeagues()).thenReturn(LeagueResponse(expectedLeagues))

        // Act
        val result = repository.getAllLeagues()

        // Assert
        assertEquals(expectedLeagues, result)
        verify(api, times(1)).getAllLeagues()
    }

    @Test
    fun `getTeamsByLeague returns list of teams`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        val expectedTeams = listOf(
            Team("1", "Manchester United", "logo_url", "Description"),
            Team("2", "Chelsea FC", "logo_url", "Description")
        )
        `when`(api.getTeamsByLeague(leagueName)).thenReturn(TeamResponse(expectedTeams))

        // Act
        val result = repository.getTeamsByLeague(leagueName)

        // Assert
        assertEquals(expectedTeams, result)
        verify(api, times(1)).getTeamsByLeague(leagueName)
    }

    @Test(expected = Exception::class)
    fun `getAllLeagues throws exception when API fails`() = runTest {
        // Arrange
        `when`(api.getAllLeagues()).thenThrow(RuntimeException("API failure"))

        // Act
        repository.getAllLeagues()

        // Assert
        // Exception is expected, so nothing to assert here.
    }

    @Test(expected = Exception::class)
    fun `getTeamsByLeague throws exception when API fails`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        `when`(api.getTeamsByLeague(leagueName)).thenThrow(RuntimeException("API failure"))

        // Act
        repository.getTeamsByLeague(leagueName)

        // Assert
        // Exception is expected, so nothing to assert here.
    }
}