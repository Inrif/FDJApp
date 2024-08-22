package com.abbesolo.fdjapp.features

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.domain.usecase.GetLeaguesUseCase
import com.abbesolo.fdjapp.domain.usecase.GetTeamsByLeagueUseCase
import com.abbesolo.fdjapp.features.displayModel.DisplayModel
import com.abbesolo.fdjapp.features.state.UiState
import com.abbesolo.fdjapp.features.transformer.toDisplayModel
import com.abbesolo.fdjapp.features.viewModels.SportsViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Created by HOUNSA ROMUALD on 22/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    private lateinit var getLeaguesUseCase: GetLeaguesUseCase
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase
    private lateinit var viewModel: SportsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getLeaguesUseCase = mock()
        getTeamsByLeagueUseCase = mock()
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `leagueStateFlow should be updated with success when fetchLeagues is called and data is returned successfully`() =
        runTest {
            // Arrange
            val leagues = listOf(
                League(
                    idLeague = "1",
                    strLeague = "Premier League",
                    strSport = "Soccer",
                    strLeagueAlternate = "EPL",
                    strBadge = "logo_url_1"
                ),
                League(
                    idLeague = "2",
                    strLeague = "La Liga",
                    strSport = "Soccer",
                    strLeagueAlternate = "LL",
                    strBadge = "logo_url_2"
                )
            )
            val expectedDisplayModels = leagues.map { it.toDisplayModel() }
            whenever(getLeaguesUseCase()).thenReturn(leagues)

            // Act
            viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase)
            advanceUntilIdle()

            // Assert
            val state = viewModel.leagueStateFlow.first()
            assertThat(state).isInstanceOf(UiState.Success::class.java)
            assertThat((state as UiState.Success).data).isEqualTo(expectedDisplayModels)
        }

    @Test
    fun `leagueStateFlow should be updated with error when fetchLeagues is called and use case throws exception`() =
        runTest {
            // Arrange
            whenever(getLeaguesUseCase()).thenThrow(RuntimeException("Failed to load leagues"))

            // Act
            viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase)
            advanceUntilIdle()

            // Assert
            val state = viewModel.leagueStateFlow.first()
            assertThat(state).isInstanceOf(UiState.Error::class.java)
            assertThat((state as UiState.Error).message).isEqualTo("Failed to load leagues")
        }

    @Test
    fun `onLeagueSelected should update teamsStateFlow with success when data is returned successfully`() =
        runTest {
            // Arrange
            val league = DisplayModel("1", "Premier League", "logo_url_1")
            val teams = listOf(
                Team(
                    idTeam = "1",
                    strTeam = "Manchester United",
                    strLogo = "Old Trafford",
                    strBadge = "logo_url_1"
                ),
                Team(
                    idTeam = "2",
                    strTeam = "Liverpool",
                    strLogo = "Anfield",
                    strBadge = "logo_url_2"
                )
            )
            val expectedDisplayModels = teams.map { it.toDisplayModel() }
                .sortedByDescending { it.name }
                .filterIndexed { index, _ -> index % 2 == 0 }

            whenever(getTeamsByLeagueUseCase(league.name)).thenReturn(teams)

            // Act
            viewModel.onLeagueSelected(league)
            advanceUntilIdle()

            // Assert
            val state = viewModel.teamsStateFlow.first()
            assertThat(state).isInstanceOf(UiState.Success::class.java)
            assertThat((state as UiState.Success).data).isEqualTo(expectedDisplayModels)
        }

    @Test
    fun `onLeagueSelected should update teamsStateFlow with error when use case throws exception`() =
        runTest {
            // Arrange
            val league = DisplayModel("1", "Premier League", "logo_url_1")
            whenever(getTeamsByLeagueUseCase(league.name)).thenThrow(RuntimeException("Failed to load teams"))

            // Act
            viewModel.onLeagueSelected(league)
            advanceUntilIdle()

            // Assert
            val state = viewModel.teamsStateFlow.first()
            assertThat(state).isInstanceOf(UiState.Error::class.java)
            assertThat((state as UiState.Error).message).isEqualTo("Failed to load teams")
        }

    @Test
    fun `onSearchQueryChanged should filter leagues correctly`() = runTest {
        // Arrange
        val leagues = listOf(
            League(
                idLeague = "1",
                strLeague = "Premier League",
                strSport = "Soccer",
                strLeagueAlternate = "EPL",
                strBadge = "logo_url_1"
            ),
            League(
                idLeague = "2",
                strLeague = "La Liga",
                strSport = "Soccer",
                strLeagueAlternate = "LL",
                strBadge = "logo_url_2"
            ),
            League(
                idLeague = "3",
                strLeague = "Serie A",
                strSport = "Soccer",
                strLeagueAlternate = "SA",
                strBadge = "logo_url_3"
            )
        )
        whenever(getLeaguesUseCase()).thenReturn(leagues)
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase)
        advanceUntilIdle()

        // Act
        viewModel.onSearchQueryChanged("La")
        advanceUntilIdle()

        // Assert
        val state = viewModel.leagueStateFlow.first()
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        val filteredLeagues = (state as UiState.Success).data
        assertThat(filteredLeagues.size).isEqualTo(1)
        assertThat(filteredLeagues[0].name).isEqualTo("La Liga")
    }

    @Test
    fun `clearSearchQuery should reset leagueStateFlow to initial state`() = runTest {
        // Arrange
        val leagues = listOf(
            League(
                idLeague = "1",
                strLeague = "Premier League",
                strSport = "Soccer",
                strLeagueAlternate = "EPL",
                strBadge = "logo_url_1"
            ),
            League(
                idLeague = "2",
                strLeague = "La Liga",
                strSport = "Soccer",
                strLeagueAlternate = "LL",
                strBadge = "logo_url_2"
            )
        )
        val expectedDisplayModels = leagues.map { it.toDisplayModel() }
        whenever(getLeaguesUseCase()).thenReturn(leagues)
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase)
        advanceUntilIdle()

        // Act
        viewModel.clearSearchQuery()
        advanceUntilIdle()

        // Assert
        val state = viewModel.leagueStateFlow.first()
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        val allLeagues = (state as UiState.Success).data
        assertThat(allLeagues).isEqualTo(expectedDisplayModels)
    }
}