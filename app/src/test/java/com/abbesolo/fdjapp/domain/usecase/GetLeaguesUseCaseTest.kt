package com.abbesolo.fdjapp.domain.usecase

import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity
import com.abbesolo.fdjapp.domain.repository.SportsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetLeaguesUseCaseTest {

    @Mock
    private lateinit var repository: SportsRepository

    private lateinit var getLeaguesUseCase: GetLeaguesUseCase
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        getLeaguesUseCase = GetLeaguesUseCase(repository, testDispatcher)
        getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke GetLeaguesUseCase returns list of LeagueEntity`() = runTest {
        // Arrange
        val leagues = listOf(
            LeagueEntity(id = "1", name = "Premier League"),
            LeagueEntity(id = "2", name = "La Liga")
        )
        whenever(repository.getAllLeagues()).thenReturn(leagues)

        // Act
        val result = getLeaguesUseCase()

        // Assert
        assertThat(result).isEqualTo(leagues)
        verify(repository).getAllLeagues()
    }

    @Test
    fun `invoke GetTeamsByLeagueUseCase returns filtered and sorted list of TeamEntity`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        val teams = listOf(
            TeamEntity(id = "1", name = "Manchester United", logoUrl = "logo_url_1"),
            TeamEntity(id = "2", name = "Chelsea FC", logoUrl = "logo_url_2"),
            TeamEntity(id = "3", name = "Liverpool", logoUrl = "logo_url_3")
        )
        whenever(repository.getTeamsByLeague(leagueName)).thenReturn(teams)

        // Act
        val result = getTeamsByLeagueUseCase(leagueName)

        // Assert
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("Manchester United")
        assertThat(result[1].name).isEqualTo("Liverpool")
        verify(repository).getTeamsByLeague(leagueName)
    }

    @Test(expected = Exception::class)
    fun `invoke GetLeaguesUseCase throws exception when repository fails`() = runTest {
        // Arrange
        whenever(repository.getAllLeagues()).thenThrow(RuntimeException("API failure"))

        // Act
        getLeaguesUseCase()

        // Assert
        // Exception is expected, so nothing to assert here.
    }

    @Test(expected = Exception::class)
    fun `invoke GetTeamsByLeagueUseCase throws exception when repository fails`() = runTest {
        // Arrange
        val leagueName = "Premier League"
        whenever(repository.getTeamsByLeague(leagueName)).thenThrow(RuntimeException("API failure"))

        // Act
        getTeamsByLeagueUseCase(leagueName)

        // Assert
        // Exception is expected, so nothing to assert here.
    }
}