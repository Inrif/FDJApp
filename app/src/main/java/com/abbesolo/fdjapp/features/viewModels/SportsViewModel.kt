package com.abbesolo.fdjapp.features.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbesolo.fdjapp.domain.usecase.GetLeaguesUseCase
import com.abbesolo.fdjapp.domain.usecase.GetTeamsByLeagueUseCase
import com.abbesolo.fdjapp.features.displayModel.DisplayModel
import com.abbesolo.fdjapp.features.state.UiState
import com.abbesolo.fdjapp.features.transformer.toDisplayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val getLeaguesUseCase: GetLeaguesUseCase,
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase
) : ViewModel() {

    private val _leagueState = MutableStateFlow<UiState<List<DisplayModel>>>(UiState.Loading)
    val leagueStateFlow: StateFlow<UiState<List<DisplayModel>>> = _leagueState

    private val _teamsState = MutableStateFlow<UiState<List<DisplayModel>>>(UiState.Loading)
    val teamsStateFlow: StateFlow<UiState<List<DisplayModel>>> = _teamsState

    private val _searchQuery = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQuery

    init {
        fetchLeagues()
    }

    private fun fetchLeagues() {
        viewModelScope.launch {
            _leagueState.value = try {
                val leagues = getLeaguesUseCase().map { it.toDisplayModel() }
                UiState.Success(leagues)
            } catch (e: Exception) {
                UiState.Error("Failed to load leagues")
            }
        }
    }

    fun onLeagueSelected(league: DisplayModel) {
        setSearchQuery(league.name)
        viewModelScope.launch {
            _teamsState.value = UiState.Loading
            _teamsState.value = try {
                val teams = getTeamsByLeagueUseCase(league.name)
                    .map { it.toDisplayModel() }
                    .sortedByDescending { it.name }
                    .filterIndexed { index, _ -> index % 2 == 0 }
                UiState.Success(teams)
            } catch (e: Exception) {
                UiState.Error("Failed to load teams")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applySearchFilter()
    }

    private fun applySearchFilter() {
        val currentLeagues = (leagueStateFlow.value as? UiState.Success)?.data ?: return
        val filteredLeagues = currentLeagues.filter {
            it.name.contains(_searchQuery.value, ignoreCase = true)
        }
        _leagueState.value = UiState.Success(filteredLeagues)
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
        _teamsState.value = UiState.Loading
        fetchLeagues()
    }

    private fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}