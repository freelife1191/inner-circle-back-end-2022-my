package com.fastcampus.tennistdd.jake.web;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TennisGameControllerAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("게임 생성 API를 호출하면 gameId가 포함된 JSON 응답을 반환한다.")
    void create() {
        final JsonPath response = RestAssured.

                given()
                .contentType(ContentType.JSON).

                when()
                .post("/api/jake/tennis-game").

                then()
                .statusCode(200)
                .extract().jsonPath();

        assertExpectedResponse(response, 1L, 0, 0, "STARTED");
    }

    @Test
    @DisplayName("서버 득점 API를 호출하면 기대하는 JSON 응답을 반환한다.")
    void serverScores() {
        final var response = RestAssured.

                given()
                .contentType(ContentType.JSON).

                when()
                .put("/api/jake/tennis-game/2/score/server").

                then()
                .statusCode(200)
                .extract().jsonPath();

        assertExpectedResponse(response, 2L, 1, 0, "STARTED");
    }

    @Test
    @DisplayName("리시버 득점 API를 호출하면 기대하는 JSON 응답을 반환한다.")
    void receiverScores() {
        final var response = RestAssured.

                given()
                .contentType(ContentType.JSON).

                when()
                .put("/api/jake/tennis-game/3/score/receiver").

                then()
                .statusCode(200)
                .extract().jsonPath();

        assertExpectedResponse(response, 3L, 0, 1, "STARTED");
    }

    private void assertExpectedResponse(final JsonPath response, final long gameId, final int server, final int receiver, final String status) {
        assertThat(response.getLong("gameId")).isGreaterThanOrEqualTo(gameId);
        assertThat(response.getInt("server")).isEqualTo(server);
        assertThat(response.getInt("receiver")).isEqualTo(receiver);
        assertThat(response.getString("status")).isEqualTo(status);
    }
}
