package com.abbesolo.fdjapp.features.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.features.displayModel.DisplayModel
import com.abbesolo.fdjapp.features.state.UiState
import com.abbesolo.fdjapp.features.viewModels.SportsViewModel


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

@Composable
fun TeamItem(team: DisplayModel) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = team.logoUrl,
            contentDescription = team.name
        )
    }
}


