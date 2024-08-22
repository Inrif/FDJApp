package com.abbesolo.fdjapp.features.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abbesolo.fdjapp.features.displayModel.DisplayModel

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

@Preview(showBackground = true)
@Composable
fun PreviewTeamItem() {
    val sampleTeam = DisplayModel(
        id = "1",
        name = "Manchester United",
        logoUrl = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
    )

    TeamItem(team = sampleTeam)
}