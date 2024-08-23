package com.abbesolo.fdjapp.data.repository

import com.abbesolo.fdjapp.data.api.SportsApi
import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.LeagueResponse
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.data.models.TeamResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SportsRepositoryImplTest {

    @Mock
    private lateinit var api: SportsApi

    private lateinit var repository: SportsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = SportsRepositoryImpl(api)
    }

    @Test
    fun `getAllLeagues returns list of LeagueEntity`() = runTest {
        // Arrange
        val leagues = listOf(
            League(idLeague = "1", strLeague = "Premier League", strSport = "Soccer", strLeagueAlternate = "EPL", strBadge = "logo_url_1"),
            League(idLeague = "2", strLeague = "La Liga", strSport = "Soccer", strLeagueAlternate = "LL", strBadge = "logo_url_2")
        )
        `when`(api.getAllLeagues()).thenReturn(LeagueResponse(leagues))

        // Act
        val result = repository.getAllLeagues()

        // Assert
        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo("1")
        assertThat(result[0].name).isEqualTo("Premier League")
        verify(api, times(1)).getAllLeagues()
    }

    @Test
    fun `getTeamsByLeague returns list of TeamEntity`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        val teams = listOf(
            Team(idTeam = "1", strTeam = "Manchester United", strLogo = "logo_url_1", strBadge = "badge_url_1"),
            Team(idTeam = "2", strTeam = "Chelsea FC", strLogo = "logo_url_2", strBadge = "badge_url_2")
        )
        `when`(api.getTeamsByLeague(leagueName)).thenReturn(TeamResponse(teams))

        // Act
        val result = repository.getTeamsByLeague(leagueName)

        // Assert
        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo("1")
        assertThat(result[0].name).isEqualTo("Manchester United")
        assertThat(result[0].logoUrl).isEqualTo("badge_url_1")
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
