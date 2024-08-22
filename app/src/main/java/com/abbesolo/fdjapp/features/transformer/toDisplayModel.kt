package com.abbesolo.fdjapp.features.transformer

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.models.Team
import com.abbesolo.fdjapp.features.displayModel.DisplayModel

fun League.toDisplayModel(): DisplayModel {
    return DisplayModel(
        id = this.idLeague,
        name = this.strLeague,
        logoUrl = this.strBadge
    )
}

fun Team.toDisplayModel(): DisplayModel {
    return DisplayModel(
        id = this.idTeam,
        name = this.strTeam,
        logoUrl = this.strBadge
    )
}
