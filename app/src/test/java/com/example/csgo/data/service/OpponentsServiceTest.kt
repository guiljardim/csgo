package com.example.csgo.data.service

import com.example.csgo.data.api.OpponentsService
import com.example.csgo.data.model.OpponentsDetailsRemote
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
class OpponentsServiceTest {

    private lateinit var service: OpponentsService
    private lateinit var server: MockWebServer


    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpponentsService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should fetch opponent correctly given 200 response`() {
        val expected = getDetailsOpponentsRemote()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(expected))
        server.enqueue(response)

        runBlocking {
            val actual = service.getOpponentsSync(12, "")


            Assert.assertEquals(200, actual.code())
        }
    }

    @Test
    fun `should not fetch opponent correctly given 400 response`() {
        val expected = getDetailsOpponentsRemote()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(Gson().toJson(expected))
        server.enqueue(response)

        runBlocking {
            val actual = service.getOpponentsSync(12, "")


            Assert.assertEquals(400, actual.code())
        }
    }

    @Test
    fun `should fetch data opponent correctly`() {
        val expected = getDetailsOpponentsRemote()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(expected))
        server.enqueue(response)

        runBlocking {
            val actual = service.getOpponentsSync(12, "")


            Assert.assertEquals(Gson().toJson(expected), Gson().toJson(actual.body()))
        }
    }
}


fun getDetailsOpponentsRemote() = OpponentsDetailsRemote(
    "", listOf(
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
