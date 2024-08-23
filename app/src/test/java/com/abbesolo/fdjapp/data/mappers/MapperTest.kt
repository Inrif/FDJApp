package com.abbesolo.fdjapp.data.mappers

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MapperTest {

    @Test
    fun `toLeagueEntity maps correctly`() {
        // Arrange
        val league = League(
            idLeague = "4328",
            strLeague = "English Premier League",
            strSport = "Soccer",
            strLeagueAlternate = "Premier League, EPL",
            strBadge = "https://www.thesportsdb.com/images/media/logo.png"
        )

        // Act
        val leagueEntity = league.toLeagueEntity()

        // Assert
        assertThat(leagueEntity.id).isEqualTo("4328")
        assertThat(leagueEntity.name).isEqualTo("English Premier League")
    }

    @Test
    fun `toLeagueEntity maps with empty badgeUrl`() {
        // Arrange
        val league = League(
            idLeague = "4328",
            strLeague = "English Premier League",
            strSport = "Soccer",
            strLeagueAlternate = "Premier League, EPL",
            strBadge = ""
        )

        // Act
        val leagueEntity = league.toLeagueEntity()

        // Assert
        assertThat(leagueEntity.id).isEqualTo("4328")
        assertThat(leagueEntity.name).isEqualTo("English Premier League")
    }

    @Test
    fun `toTeamEntity maps correctly`() {
        // Arrange
        val team = Team(
            idTeam = "133602",
            strTeam = "Manchester United",
            strLogo = "https://www.thesportsdb.com/images/media/team/logo.png",
            strBadge = "https://www.thesportsdb.com/images/media/team/badge.png"
        )

        // Act
        val teamEntity = team.toTeamEntity()

        // Assert
        assertThat(teamEntity.id).isEqualTo("133602")
        assertThat(teamEntity.name).isEqualTo("Manchester United")
        assertThat(teamEntity.logoUrl).isEqualTo("https://www.thesportsdb.com/images/media/team/badge.png")
    }

    @Test
    fun `toTeamEntity maps with empty logoUrl`() {
        // Arrange
        val team = Team(
            idTeam = "133602",
            strTeam = "Manchester United",
            strLogo = "https://www.thesportsdb.com/images/media/team/logo.png",
            strBadge = ""
        )

        // Act
        val teamEntity = team.toTeamEntity()

        // Assert
        assertThat(teamEntity.id).isEqualTo("133602")
        assertThat(teamEntity.name).isEqualTo("Manchester United")
        assertThat(teamEntity.logoUrl).isEqualTo("")
    }
}