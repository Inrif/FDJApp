package com.abbesolo.fdjapp.features.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbesolo.fdjapp.features.displayModel.DisplayModel

@Composable
fun LeagueItem(league: DisplayModel, onLeagueClick: (DisplayModel) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onLeagueClick(league) }),
        text = league.name,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLeagueItem() {
    val sampleLeague = DisplayModel(
        id = "1",
        name = "Premier League",
        logoUrl = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
    )
    LeagueItem(league = sampleLeague, onLeagueClick = {})
}