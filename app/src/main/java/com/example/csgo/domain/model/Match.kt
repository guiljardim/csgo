package com.example.csgo.domain.model

import kotlinx.serialization.Serializable
import java.util.*

data class Match(
    val id: Int?,
    val begin_at: Date?,
    val leagueName: String?,
    val leagueIcon: String?,
    val serieName: String?,
    val status: String?,
    val opponents: List<Opponent>?
) {
    @Serializable
    data class Opponent(
        val id: Int?,
        val image_url: String?,
        val name: String?,
    )
}

