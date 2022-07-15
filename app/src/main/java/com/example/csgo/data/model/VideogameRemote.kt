package com.example.csgo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VideogameRemote(
    val id: Int,
    val name: String,
    val slug: String,
)