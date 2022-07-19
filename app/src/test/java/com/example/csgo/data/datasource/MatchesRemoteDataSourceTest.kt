package com.example.csgo.data.datasource

import com.example.csgo.data.api.MatchesService
import com.example.csgo.data.model.MatchRemote
import com.example.csgo.data.model.OpponentsRemote
import com.example.csgo.data.model.VideogameRemote
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.*


@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class MatchesRemoteDataSourceTest {


    private lateinit var matchesRemoteDataSource: MatchesRemoteDataSource
    private lateinit var service: MatchesService
    private lateinit var server: MockWebServer


    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MatchesService::class.java)
        matchesRemoteDataSource = MatchesRemoteDataSource(service)

    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should get matches from data source correctly given 200 response`() {
        val expected = getListOfMatchRemote()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(expected))
        server.enqueue(response)

        runBlocking {
            val actual = matchesRemoteDataSource.invoke("", "", "", 1, 2)


            Assert.assertEquals(200, actual.code())
        }
    }

    @Test
    fun `should not get matches from data source correctly given 400 response`() {
        val expected = getListOfMatchRemote()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(Gson().toJson(expected))
        server.enqueue(response)

        runBlocking {
            val actual = matchesRemoteDataSource.invoke("", "", "", 1, 2)


            Assert.assertEquals(400, actual.code())
        }
    }

    @Test
    fun `should get data from data source matches correctly`() {
        val expected = getListOfMatchRemote()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(expected))
        server.enqueue(response)

        runBlocking {
            val actual = matchesRemoteDataSource.invoke("", "", "", 1, 2)


            Assert.assertEquals(Gson().toJson(expected), Gson().toJson(actual.body()))
        }
    }
}


fun getListOfMatchRemote() = listOf(
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
        Date(),
        1,
        true,
        "",
        MatchRemote.LeagueRemote(1, "", Date(), "", "")
    )
)
