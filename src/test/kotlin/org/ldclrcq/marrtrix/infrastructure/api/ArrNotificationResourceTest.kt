package org.ldclrcq.marrtrix.infrastructure.api

import io.mockk.coEvery
import io.mockk.slot
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.enterprise.inject.Default
import jakarta.ws.rs.core.Response.Status
import org.junit.jupiter.api.Test
import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage
import org.ldclrcq.marrtrix.domain.matrix.MatrixNotifier
import org.ldclrcq.marrtrix.domain.radarr.webhook.RadarrMatrixMessage
import strikt.api.expectThat
import strikt.assertions.first
import strikt.assertions.isEqualTo

@QuarkusTest
class ArrNotificationResourceTest {
    @InjectMock
    @field: Default
    lateinit var matrixNotifier: MatrixNotifier

    @Test
    @TestSecurity(user = "marrtrix")
    fun `Given Radarr test notification, should send message with valid title`() {
        val slot = slot<MatrixMessage>()
        coEvery { matrixNotifier.sendMessage(notification = capture(slot))  } returns Unit

        // ARRANGE
        given().contentType(ContentType.JSON)
            .and().body(
                """
                {
                  "movie": {
                    "id": 1,
                    "title": "Test Title",
                    "year": 1970,
                    "releaseDate": "1970-01-01",
                    "folderPath": "C:\\testpath",
                    "tmdbId": 0
                  },
                  "remoteMovie": {
                    "tmdbId": 1234,
                    "imdbId": "5678",
                    "title": "Test title",
                    "year": 1970
                  },
                  "release": {
                    "quality": "Test Quality",
                    "qualityVersion": 1,
                    "releaseGroup": "Test Group",
                    "releaseTitle": "Test Title",
                    "indexer": "Test Indexer",
                    "size": 9999999,
                    "customFormatScore": 0
                  },
                  "eventType": "Test",
                  "instanceName": "Radarr",
                  "applicationUrl": ""
                }
            """.trimIndent()
            )
            .`when`().post("/radarr")
            .then().statusCode(Status.NO_CONTENT.statusCode)


        expectThat(slot.captured as RadarrMatrixMessage) {
            get(RadarrMatrixMessage::title).isEqualTo("\uD83C\uDFA5 Test - Test Title (1970)")
        }
    }

    @Test
    @TestSecurity(user = "marrtrix")
    fun `Given Radarr movie added notification, should send message with valid title`() {
        // ARRANGE
        val slot = slot<MatrixMessage>()
        coEvery { matrixNotifier.sendMessage(notification = capture(slot)) } returns Unit
        given().contentType(ContentType.JSON)
            .and().body(
                """
                {
  "movie": {
    "id": 659,
    "title": "Mission: Impossible - Dead Reckoning Part One",
    "year": 2023,
    "releaseDate": "2023-10-08",
    "folderPath": "/media/Movies/Mission Impossible Dead Reckoning Part One (2023) [imdbid-tt9603212]",
    "tmdbId": 575264,
    "imdbId": "tt9603212",
    "overview": "The seventh installment of the Mission: Impossible franchise."
  },
  "addMethod": "manual",
  "eventType": "MovieAdded",
  "instanceName": "Radarr",
  "applicationUrl": ""
}
            """.trimIndent()
            )
            .`when`().post("/radarr")
            .then().statusCode(Status.NO_CONTENT.statusCode)


        expectThat(slot.captured as RadarrMatrixMessage) {
            get(RadarrMatrixMessage::title).isEqualTo("\uD83C\uDFA5 Movie added - Mission: Impossible - Dead Reckoning Part One (2023)")
        }
    }

    @Test
    @TestSecurity(user = "marrtrix")
    fun `Given Radarr movie grabbed notification, should send message with valid title`() {
        // ARRANGE
        val slot = slot<MatrixMessage>()
        coEvery { matrixNotifier.sendMessage(notification = capture(slot)) } returns Unit

        given().contentType(ContentType.JSON)
            .and().body(
                """
                {
  "movie": {
    "id": 659,
    "title": "Mission: Impossible - Dead Reckoning Part One",
    "year": 2023,
    "releaseDate": "2023-10-08",
    "folderPath": "/media/Movies/Mission Impossible Dead Reckoning Part One (2023) [imdbid-tt9603212]",
    "tmdbId": 575264,
    "imdbId": "tt9603212",
    "overview": "The seventh installment of the Mission: Impossible franchise."
  },
  "addMethod": "manual",
  "eventType": "Grab",
  "instanceName": "Radarr",
  "applicationUrl": ""
}
            """.trimIndent()
            )
            .`when`().post("/radarr")
            .then().statusCode(Status.NO_CONTENT.statusCode)


        expectThat(slot.captured as RadarrMatrixMessage) {
            get(RadarrMatrixMessage::title).isEqualTo("\uD83C\uDFA5 New movie grabbed - Mission: Impossible - Dead Reckoning Part One (2023)")
        }
    }

    @Test
    @TestSecurity(user = "marrtrix")
    fun `Given Radarr movie downloaded notification, should send message with valid title`() {
        // ARRANGE
        val slot = slot<MatrixMessage>()
        coEvery { matrixNotifier.sendMessage(notification = capture(slot)) } returns Unit

        given().contentType(ContentType.JSON)
            .and().body(
                """
                {
  "movie": {
    "id": 659,
    "title": "Mission: Impossible - Dead Reckoning Part One",
    "year": 2023,
    "releaseDate": "2023-10-08",
    "folderPath": "/media/Movies/Mission Impossible Dead Reckoning Part One (2023) [imdbid-tt9603212]",
    "tmdbId": 575264,
    "imdbId": "tt9603212",
    "overview": "The seventh installment of the Mission: Impossible franchise."
  },
  "addMethod": "manual",
  "eventType": "Download",
  "instanceName": "Radarr",
  "applicationUrl": ""
}
            """.trimIndent()
            )
            .`when`().post("/radarr")
            .then().statusCode(Status.NO_CONTENT.statusCode)

        expectThat(slot.captured as RadarrMatrixMessage) {
            get(RadarrMatrixMessage::title).isEqualTo("\uD83C\uDFA5 New movie downloaded - Mission: Impossible - Dead Reckoning Part One (2023)")
        }
    }

    @Test
    fun `Calling radarr endpoint without basic auth, should fail with 401 response`() {
        // ARRANGE
        val slot = slot<MatrixMessage>()
        coEvery { matrixNotifier.sendMessage(notification = capture(slot)) } returns Unit

        given().contentType(ContentType.JSON)
            .and().body("{}")
            .`when`().post("/radarr")
            .then().statusCode(Status.UNAUTHORIZED.statusCode)
    }
}