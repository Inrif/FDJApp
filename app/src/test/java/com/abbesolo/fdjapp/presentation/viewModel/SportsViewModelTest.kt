package com.abbesolo.fdjapp.presentation.viewModel


import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity
import com.abbesolo.fdjapp.domain.usecase.GetLeaguesUseCase
import com.abbesolo.fdjapp.domain.usecase.GetTeamsByLeagueUseCase
import com.abbesolo.fdjapp.presentation.displayModel.DisplayModel
import com.abbesolo.fdjapp.presentation.state.UiState
import com.abbesolo.fdjapp.presentation.viewModels.SportsViewModel
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
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    @Mock
    private lateinit var getLeaguesUseCase: GetLeaguesUseCase

    @Mock
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase

    private lateinit var viewModel: SportsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `leagueStateFlow is updated with Success when viewModel is initialized and use case returns data`() = runTest {
        // Arrange
        val leagues = listOf(
            LeagueEntity(id = "1", name = "Premier League"),
            LeagueEntity(id = "2", name = "Second League")
        )
        whenever(getLeaguesUseCase()).thenReturn(leagues)

        // Act
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.leagueStateFlow.value
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        val successState = state as UiState.Success
        assertThat(successState.data).hasSize(2)
        assertThat(successState.data[0].name).isEqualTo("Premier League")
        assertThat(successState.data[1].name).isEqualTo("Second League")
    }


    @Test
    fun `leagueStateFlow is updated with Error when viewModel is initialized and use case throws exception`() = runTest {
        // Arrange
        whenever(getLeaguesUseCase()).thenThrow(RuntimeException("API failure"))

        // Act
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.leagueStateFlow.value
        assertThat(state).isInstanceOf(UiState.Error::class.java)
    }

    @Test
    fun `onLeagueSelected updates teamsStateFlow with Success when use case returns data`() = runTest {
        // Arrange
        val league = DisplayModel(id = "1", name = "Premier League", logoUrl = "logo_url_1")
        val teams = listOf(
            TeamEntity(id = "1", name = "Manchester United", logoUrl = "logo_url_1"),
            TeamEntity(id = "2", name = "Liverpool", logoUrl = "logo_url_2")
        )
        whenever(getTeamsByLeagueUseCase(league.name)).thenReturn(teams)

        // Act
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)
        viewModel.onLeagueSelected(league)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.teamsStateFlow.value
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        val successState = state as UiState.Success
        assertThat(successState.data).hasSize(2)
        assertThat(successState.data[0].name).isEqualTo("Manchester United")
    }

    @Test
    fun `onLeagueSelected updates teamsStateFlow with Error when use case throws exception`() = runTest {
        // Arrange
        val league = DisplayModel(id = "1", name = "Premier League", logoUrl = "logo_url_1")
        whenever(getTeamsByLeagueUseCase(league.name)).thenThrow(RuntimeException("API failure"))

        // Act
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)
        viewModel.onLeagueSelected(league)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.teamsStateFlow.value
        assertThat(state).isInstanceOf(UiState.Error::class.java)
    }
    @Test
    fun `onSearchQueryChanged filters leagueStateFlow correctly`() = runTest {
        // Arrange
        val leagues = listOf(
            LeagueEntity(id = "1", name = "Premier League"),
            LeagueEntity(id = "2", name = "La Liga"),
            LeagueEntity(id = "3", name = "Serie A")
        )
        whenever(getLeaguesUseCase()).thenReturn(leagues)

        // Initialize ViewModel
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()

        // Act
        viewModel.onSearchQueryChanged("La")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.leagueStateFlow.value
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        val successState = state as UiState.Success
        assertThat(successState.data).hasSize(1)
        assertThat(successState.data[0].name).isEqualTo("La Liga")
    }

    @Test
    fun `clearSearchQuery resets leagueStateFlow to initial state`() = runTest {
        // Arrange
        val leagues = listOf(
            LeagueEntity(id = "1", name = "Premier League"),
            LeagueEntity(id = "2", name = "La Liga")
        )
        whenever(getLeaguesUseCase()).thenReturn(leagues)

        // Initialize ViewModel
        viewModel = SportsViewModel(getLeaguesUseCase, getTeamsByLeagueUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()

        // Act
        viewModel.clearSearchQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.leagueStateFlow.value
        assertThat(state).isInstanceOf(UiState.Success::class.java)
        val successState = state as UiState.Success
        assertThat(successState.data).hasSize(2)
        assertThat(successState.data[0].name).isEqualTo("Premier League")
    }
}
