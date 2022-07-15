package com.example.csgo.data.model

import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class OpponentsRemote(val opponent_type: String, val opponents: List<OpponentRemote>) {


    @Serializable
    data class OpponentRemote(
        val acronym: String,
        val current_videogame: VideogameRemote,
        val id: Int,
        val image_url: String,
        val location: String,
        val modified_at: Date,
        val name: String,
        val players: List<PlayerRemote>,
        val slug: String
    )

    data class PlayerRemote(
        val age: Int,
        val birth_year: Int,
        val birthday: Date,
        val first_name: String,
        val hometown: String,
        val id: Int,
        val image_url: String,
        val last_name: String,
        val name: String,
        val nationality: String,
        val role: String,
        val slug: String
    )
}