package com.abbesolo.fdjapp.presentation.transformer

import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EntityToDisplayModelMapperTest {

    @Test
    fun `toDisplayModel maps LeagueEntity to DisplayModel with null logoUrl`() {
        // Arrange
        val leagueEntity = LeagueEntity(
            id = "4328",
            name = "English Premier League"
        )

        // Act
        val displayModel = leagueEntity.toDisplayModel()

        // Assert
        assertThat(displayModel.id).isEqualTo("4328")
        assertThat(displayModel.name).isEqualTo("English Premier League")
        assertThat(displayModel.logoUrl).isNull()
    }

    @Test
    fun `toDisplayModel maps TeamEntity to DisplayModel correctly`() {
        // Arrange
        val teamEntity = TeamEntity(
            id = "133602",
            name = "Manchester United",
            logoUrl = "https://www.thesportsdb.com/images/media/team/badge.png"
        )

        // Act
        val displayModel = teamEntity.toDisplayModel()

        // Assert
        assertThat(displayModel.id).isEqualTo("133602")
        assertThat(displayModel.name).isEqualTo("Manchester United")
        assertThat(displayModel.logoUrl).isEqualTo("https://www.thesportsdb.com/images/media/team/badge.png")
    }

    @Test
    fun `toDisplayModel maps TeamEntity with empty logoUrl to DisplayModel`() {
        // Arrange
        val teamEntity = TeamEntity(
            id = "133602",
            name = "Manchester United",
            logoUrl = ""
        )

        // Act
        val displayModel = teamEntity.toDisplayModel()

        // Assert
        assertThat(displayModel.id).isEqualTo("133602")
        assertThat(displayModel.name).isEqualTo("Manchester United")
        assertThat(displayModel.logoUrl).isEqualTo("")
    }
}
