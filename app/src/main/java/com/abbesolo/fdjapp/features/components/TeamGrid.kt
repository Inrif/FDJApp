package com.abbesolo.fdjapp.features.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbesolo.fdjapp.features.displayModel.DisplayModel

@Composable
fun TeamGrid(
    teams: List<DisplayModel>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(teams) { team ->
            TeamItem(team = team)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamGrid() {
    val sampleTeams = listOf(
        DisplayModel(
            id = "1",
            name = "Manchester United",
            logoUrl = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
        ),
        DisplayModel(
            id = "2",
            name = "Real Madrid",
            logoUrl = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
        ),
        DisplayModel(
            id = "3",
            name = "Barcelona",
            logoUrl = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
        ),
        DisplayModel(
            id = "4",
            name = "Chelsea",
            logoUrl = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
        )
    )

    TeamGrid(teams = sampleTeams)
}
