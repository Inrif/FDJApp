package com.abbesolo.fdjapp.domain

import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.data.repositories.SportsRepository
import com.abbesolo.fdjapp.domain.usecase.GetTeamsByLeagueUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Created by HOUNSA ROMUALD on 22/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */

@ExperimentalCoroutinesApi
class GetTeamsByLeagueUseCaseTest {

    private lateinit var repository: SportsRepository
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase

    @Before
    fun setUp() {
        repository = mock()
        getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(repository)
    }

    @Test
    fun `invoke should return filtered and sorted list of teams when repository returns data successfully`() =
        runTest {
            // Arrange
            val leagueName = "Premier League"
            val teamsFromRepository = listOf(
                Team(
                    idTeam = "1",
                    strTeam = "Arsenal",
                    strLogo = "Emirates Stadium",
                    strBadge = "badge_url_1"
                ),
                Team(
                    idTeam = "2",
                    strTeam = "Chelsea",
                    strLogo = "Stamford Bridge",
                    strBadge = "badge_url_2"
                ),
                Team(
                    idTeam = "3",
                    strTeam = "Liverpool",
                    strLogo = "Anfield",
                    strBadge = "badge_url_3"
                ),
                Team(
                    idTeam = "4",
                    strTeam = "Manchester United",
                    strLogo = "Old Trafford",
                    strBadge = "badge_url_4"
                )
            )
            val expectedTeams = listOf(
                Team(
                    idTeam = "3",
                    strTeam = "Liverpool",
                    strLogo = "Anfield",
                    strBadge = "badge_url_3"
                ),
                Team(
                    idTeam = "1",
                    strTeam = "Arsenal",
                    strLogo = "Emirates Stadium",
                    strBadge = "badge_url_1"
                )
            )
            whenever(repository.getTeamsByLeague(leagueName)).thenReturn(teamsFromRepository)

            // Act
            val result = getTeamsByLeagueUseCase(leagueName)

            // Assert
            assertThat(result).isEqualTo(expectedTeams)
            verify(repository, times(1)).getTeamsByLeague(leagueName)
            verifyNoMoreInteractions(repository)
        }

    @Test
    fun `invoke should throw exception when repository throws exception`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        val exception = RuntimeException("Network Error")
        whenever(repository.getTeamsByLeague(leagueName)).thenThrow(exception)

        // Act & Assert
        try {
            getTeamsByLeagueUseCase(leagueName)
            assert(false) { "Exception was expected but not thrown" }
        } catch (e: Exception) {
            assertThat(e).isEqualTo(exception)
        }
        verify(repository, times(1)).getTeamsByLeague(leagueName)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        whenever(repository.getTeamsByLeague(leagueName)).thenReturn(emptyList())

        // Act
        val result = getTeamsByLeagueUseCase(leagueName)

        // Assert
        assertThat(result).isEmpty()
        verify(repository, times(1)).getTeamsByLeague(leagueName)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `invoke should handle single element list correctly`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        val teamsFromRepository = listOf(
            Team(
                idTeam = "1",
                strTeam = "Arsenal",
                strLogo = "Emirates Stadium",
                strBadge = "badge_url_1"
            )
        )
        val expectedTeams = listOf(
            Team(
                idTeam = "1",
                strTeam = "Arsenal",
                strLogo = "Emirates Stadium",
                strBadge = "badge_url_1"
            )
        )
        whenever(repository.getTeamsByLeague(leagueName)).thenReturn(teamsFromRepository)

        // Act
        val result = getTeamsByLeagueUseCase(leagueName)

        // Assert
        assertThat(result).isEqualTo(expectedTeams)
        verify(repository, times(1)).getTeamsByLeague(leagueName)
        verifyNoMoreInteractions(repository)
    }
}
