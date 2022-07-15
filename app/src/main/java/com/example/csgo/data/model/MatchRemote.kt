package com.example.csgo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MatchRemote(
    val winner: WinnerRemote,
    val serie_id: Int,
    val official_stream_url: String,
    val league_id: Int,
    val scheduled_at: Date,
    val modified_at: Date,
    val serie: SerieRemote,
    val opponents: List<OpponentRemote>,
    val detailed_stats: Boolean,
    val rescheduled: Boolean,
    val streams_list: List<StreamRemote>,
    val videogame_version: String,
    val tournament: TournamentRemote,
    val id: Int,
    val original_scheduled_at: Date,
    val streams: StreamsRemote,
    val draw: Boolean,
    val videogame: VideogameRemote,
    val slug: String,
    val results: List<ResultRemote>,
    val games: List<GameRemote>,
    val game_advantage: String,
    val winner_id: Int,
    val status: String,
    val end_at: Date,
    val number_of_games: Int,
    val live: LiveRemote,
    val match_type: String,
    val live_embed_url: String,
    val begin_at: Date,
    val tournament_id: Int,
    val forfeit: Boolean,
    val name: String,
    val league: LeagueRemote
) {
    @Serializable
    data class WinnerRemote(
        val acronym: String,
        val id: Int,
        val image_url: String,
        val location: String,
        val modified_at: Date,
        val name: String,
        val slug: String
    )

    @Serializable
    data class SerieRemote(
        val begin_at: Date,
        val description: String,
        val end_at: Date,
        val full_name: String,
        val id: Int,
        val league_id: Int,
        val modified_at: Date,
        val name: String,
        val season: String,
        val slug: String,
        val tier: String,
        val winner_id: Int,
        val winner_type: String,
        val year: Int,
    )

    @Serializable
    data class OpponentRemote(
        val acronym: String,
        val id: Int,
        val image_url: String,
        val location: String,
        val modified_at: Date,
        val name: String,
        val slug: String
    )

    @Serializable
    data class StreamRemote(
        override var embed_url: String,
        val language: String,
        val main: Boolean,
        val official: Boolean,
        override var raw_url: String,
    ) : Stream()

    @Serializable
    data class TournamentRemote(
        val begin_at: Date,
        val end_at: Date,
        val id: Int,
        val league_id: Int,
        val live_supported: Boolean,
        val modified_at: Date,
        val name: String,
        val prizepool: String,
        val serie_id: Int,
        val slug: String,
        val tier: String,
        val winner_id: Int,
        val winner_type: String,
    )

    abstract class Stream {
        abstract var embed_url: String
        abstract var raw_url: String
    }

    @Serializable
    data class StreamsRemote(
        @SerialName("english")
        val englishRemote: EnglishRemote,
        @SerialName("official")
        val officialRemote: OfficialRemote,
        @SerialName("russian")
        val russianRemote: RussianRemote
    ) {
        @Serializable
        data class EnglishRemote(
            override var embed_url: String,
            override var raw_url: String
        ) : Stream()

        @Serializable
        data class OfficialRemote(
            override var embed_url: String,
            override var raw_url: String
        ) : Stream()

        @Serializable
        data class RussianRemote(
            override var embed_url: String,
            override var raw_url: String
        ) : Stream()
    }

    @Serializable
    data class VideogameRemote(
        val id: Int,
        val name: String,
        val slug: String,
    )

    @Serializable
    data class ResultRemote(val score: Int, val team_id: Int)

    @Serializable
    data class GameRemote(
        val begin_at: Date,
        val complete: Boolean,
        val detailed_stats: Boolean,
        val end_at: Date,
        val finished: Boolean,
        val forfeit: Boolean,
        val id: Int,
        val length: Int,
        val match_id: Int,
        val position: Int,
        val status: String,
        val video_url: String,
        val winner: Winner,
        val winner_type: String,
    ) {
        @Serializable
        data class Winner(val id: Int, val type: String)
    }

    @Serializable
    data class LiveRemote(val opens_at: Date, val supported: Boolean, val url: String)

    @Serializable
    data class LeagueRemote(
        val id: Int,
        val image_url: String,
        val modified_at: Date,
        val name: String,
        val slug: String
    )


}
