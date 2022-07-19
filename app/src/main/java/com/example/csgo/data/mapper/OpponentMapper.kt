package com.example.csgo.data.mapper

import com.example.csgo.data.model.OpponentsDetailsRemote
import com.example.csgo.domain.model.Match.Opponent
import com.example.csgo.domain.model.Match.Player


fun OpponentsDetailsRemote.mapToOpponent(): List<Opponent>? {
    return this.opponents?.map {
        val player = it.players?.map { player ->
            Player(
                player.image_url,
                "${player.first_name} ${player.last_name}",
                player.name
            )
        }

        Opponent(id = it.id, it.image_url, it.name, player)
    }

}