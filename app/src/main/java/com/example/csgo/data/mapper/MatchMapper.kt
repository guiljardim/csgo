package com.example.csgo.data.mapper

import com.example.csgo.data.model.MatchRemote
import com.example.csgo.domain.model.Match

fun List<MatchRemote>.mapToMatch(): List<Match> {
    return this.map {
        val opponents = it.opponents.map { opponents ->
            Match.Opponent(
                opponents.opponent.id,
                opponents.opponent.image_url,
                opponents.opponent.name,
            )
        }

        Match(
            it.id,
            it.begin_at,
            it.league.name,
            it.league.image_url,
            it.serie.name,
            it.status,
            opponents
        )
    }

}
