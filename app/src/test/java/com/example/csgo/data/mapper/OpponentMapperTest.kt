package com.example.csgo.data.mapper

import com.example.csgo.data.model.OpponentsDetailsRemote
import com.example.csgo.data.model.OpponentsRemote
import com.example.csgo.data.model.VideogameRemote
import com.example.csgo.domain.model.Match
import org.junit.Assert
import org.junit.Test
import java.util.*

class OpponentMapperTest {

    @Test
    fun `should map Opponent remote to Match`() {

        val actual = listOf(Match.Opponent(id = 1, "", "", listOf(Match.Player("", " ", ""))))
        Assert.assertEquals(getListOfOpponentRemoteToMap().mapToOpponent(), actual)

    }
}

fun getListOfOpponentRemoteToMap() =
    OpponentsDetailsRemote(
        "",
        listOf(
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
    )

