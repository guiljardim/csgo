package com.example.csgo.data.mapper

import com.example.csgo.data.model.MatchRemote
import com.example.csgo.data.model.OpponentsRemote
import com.example.csgo.data.model.VideogameRemote
import com.example.csgo.domain.model.Match
import org.junit.Assert
import org.junit.Test
import java.util.*

class MatchMapperTest {

    @Test
    fun `should map Match remote to Match`() {

        val actual = arrayListOf(
            Match(
                2,
                Date(123),
                "",
                "",
                "",
                "",
                listOf(Match.Opponent(1, "", "", null)),
                12
            )
        )
        Assert.assertEquals(getListOfMatchRemoteToMap().mapToMatch(12), actual)

    }
}

fun getListOfMatchRemoteToMap() = listOf(
    MatchRemote(
        MatchRemote.WinnerRemote("", 1, "", "", Date(), "", ""),
        1,
        "",
        2,
        Date(),
        Date(),
        MatchRemote.SerieRemote(
            Date(),
            "",
            Date(),
            "",
            1,
            1,
            Date(),
            "",
            "",
            "",
            "",
            1,
            "",
            1,
        ),
        listOf(
            OpponentsRemote(
                "",
                OpponentsRemote.OpponentRemote(
                    "",
                    VideogameRemote(1, "", ""),
                    1,
                    "",
                    "",
                    Date(),
                    "",
                    listOf(
                        OpponentsRemote.PlayerRemote(
                            22,
                            2019,
                            Date(),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                    ),
                    ""
                )
            )
        ),
        true,
        rescheduled = true,
        listOf(
            MatchRemote.StreamRemote(
                "", "",
                main = false,
                official = true,
                raw_url = ""
            )
        ),
        "",
        MatchRemote.TournamentRemote(
            Date(),
            Date(),
            3,
            4,
            true,
            Date(),
            "",
            "",
            5,
            "",
            "",
            5,
            ""
        ),
        2,
        Date(),
        MatchRemote.StreamsRemote(
            MatchRemote.StreamsRemote.EnglishRemote("", ""),
            MatchRemote.StreamsRemote.OfficialRemote("", ""),
            MatchRemote.StreamsRemote.RussianRemote("", "")
        ),
        true,
        VideogameRemote(9, "", ""),
        "",
        listOf(MatchRemote.ResultRemote(1, 2)),
        listOf(
            MatchRemote.GameRemote(
                Date(),
                true,
                true,
                Date(),
                true,
                true,
                9,
                9,
                1,
                2,
                "",
                "",
                MatchRemote.GameRemote.Winner(1, ""),
                "",
            )
        ),
        "",
        1,
        "",
        Date(),
        1,
        MatchRemote.LiveRemote(Date(), true, ""),
        "",
        "",
        Date(123),
        1,
        true,
        "",
        MatchRemote.LeagueRemote(1, "", Date(), "", "")
    )
)