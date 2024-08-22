package com.abbesolo.fdjapp.features.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abbesolo.fdjapp.R
import com.abbesolo.fdjapp.features.displayModel.DisplayModel
import com.abbesolo.fdjapp.features.state.UiState
import com.abbesolo.fdjapp.features.viewModels.SportsViewModel

@Composable
fun LeagueSearchScreen(
    viewModel: SportsViewModel = hiltViewModel()
) {
    val leagueState by viewModel.leagueStateFlow.collectAsState()
    val teamsState by viewModel.teamsStateFlow.collectAsState()
    val searchQuery by viewModel.searchQueryFlow.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = viewModel::onSearchQueryChanged,
                onClearSearch = viewModel::clearSearchQuery
            )
        }
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            leagueState = leagueState,
            teamsState = teamsState,
            onLeagueSelected = viewModel::onLeagueSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                placeholder = {
                    Text(text = stringResource(R.string.search_leagues))
                },
                leadingIcon = {
                    if (searchQuery.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = stringResource(
                                R.string.search
                            )
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        Row {
                            IconButton(onClick = onClearSearch) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(
                                        R.string.clear_search
                                    )
                                )
                            }
                            Text(
                                text = stringResource(R.string.cancel),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .clickable(onClick = onClearSearch)
                            )
                        }
                    }
                },
                singleLine = true
            )
        }
    )
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    leagueState: UiState<List<DisplayModel>>,
    teamsState: UiState<List<DisplayModel>>,
    onLeagueSelected: (DisplayModel) -> Unit
) {
    Column(modifier = modifier) {
        when (leagueState) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is UiState.Success -> {
                val leagues = leagueState.data

                if (teamsState is UiState.Loading) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(leagues) { league ->
                            LeagueItem(league = league, onLeagueClick = onLeagueSelected)
                        }
                    }
                } else if (teamsState is UiState.Success) {
                    TeamGrid(teams = teamsState.data)
                }
            }

            is UiState.Error -> Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = leagueState.message,
                color = Color.Red
            )
        }
    }
}


@Composable
fun LeagueItem(league: DisplayModel, onLeagueClick: (DisplayModel) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onLeagueClick(league) }),
        text = league.name,
        style = MaterialTheme.typography.bodySmall,
    )
}