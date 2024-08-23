package com.abbesolo.fdjapp.data.mappers

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity

fun League.toLeagueEntity(): LeagueEntity {
    return LeagueEntity(
        id = idLeague,
        name = strLeague
    )
}

fun Team.toTeamEntity(): TeamEntity {
    return TeamEntity(
        id = idTeam,
        name = strTeam,
        logoUrl = strBadge
    )
}