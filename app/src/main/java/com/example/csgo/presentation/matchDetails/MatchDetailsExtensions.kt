package com.example.csgo.presentation.matchDetails

import com.example.csgo.domain.model.Match

fun List<Match.Player>.mapToShow(player: List<Match.Player>?): List<PlayerToShow> {
    val playerToShow: MutableList<PlayerToShow> = mutableListOf()
    for ((i, v) in this.withIndex()) {
        playerToShow.add(
            PlayerToShow(
                v.name,
                v.nickname,
                v.image_url,
                player?.get(i)?.name,
                player?.get(i)?.nickname,
                player?.get(i)?.image_url
            )
        )
    }
    return playerToShow
}
