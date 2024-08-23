package com.abbesolo.fdjapp.presentation.transformer

import com.abbesolo.fdjapp.domain.models.LeagueEntity
import com.abbesolo.fdjapp.domain.models.TeamEntity
import com.abbesolo.fdjapp.presentation.displayModel.DisplayModel

fun LeagueEntity.toDisplayModel(): DisplayModel {
    return DisplayModel(
        id = this.id,
        name = this.name,
        logoUrl = null
    )
}

fun TeamEntity.toDisplayModel(): DisplayModel {
    return DisplayModel(
        id = this.id,
        name = this.name,
        logoUrl = this.logoUrl
    )
}