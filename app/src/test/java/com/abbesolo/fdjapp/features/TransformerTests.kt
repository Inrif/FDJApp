package com.abbesolo.fdjapp.features

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.features.transformer.toDisplayModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by HOUNSA ROMUALD on 22/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */

class TransformerTests {

    @Test
    fun `toDisplayModel should correctly map League to DisplayModel`() {
        // Arrange
        val league = League(
            idLeague = "123",
            strLeague = "Premier League",
            strSport = "Soccer",
            strLeagueAlternate = "EPL",
            strBadge = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
        )

        // Act
        val result = league.toDisplayModel()

        // Assert
        assertThat(result.id).isEqualTo(league.idLeague)
        assertThat(result.name).isEqualTo(league.strLeague)
        assertThat(result.logoUrl).isEqualTo(league.strBadge)
    }

    @Test
    fun `toDisplayModel should correctly map Team to DisplayModel`() {
        // Arrange
        val team = Team(
            idTeam = "456",
            strTeam = "Manchester United",
            strLogo = "Old Trafford",
            strBadge = "https://www.thesportsdb.com/images/media/team/badge/ix6q4w1678808069.png"
        )

        // Act
        val result = team.toDisplayModel()

        // Assert
        assertThat(result.id).isEqualTo(team.idTeam)
        assertThat(result.name).isEqualTo(team.strTeam)
        assertThat(result.logoUrl).isEqualTo(team.strBadge)
    }
}
