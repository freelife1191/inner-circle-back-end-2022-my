package com.fastcampus.tennistdd.mskwon;


import com.fastcampus.tennistdd.mskwon.domain.Player;
import com.fastcampus.tennistdd.mskwon.domain.Receiver;
import com.fastcampus.tennistdd.mskwon.domain.Server;
import com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameAwards;
import com.fastcampus.tennistdd.mskwon.repository.TennisGameRepository;
import com.fastcampus.tennistdd.mskwon.service.TennisGameService;
import com.fastcampus.tennistdd.mskwon.service.TennisGameSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.AD_IN;
import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.AD_OUT;
import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.DEUCE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.NONE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType.RECEIVER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType.SERVER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameAwards.LOSER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameAwards.WINNER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameRule.BASE_COMPARE_SCORE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameRule.BASE_VICTORY_SCORE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TennisGameTest {

    Player server;
    Player receiver;

    @BeforeEach
    public void setUp() {
        // ??? ???????????? server, receiver ????????? ??????
        server = Server.of();
        receiver = Receiver.of();
    }

    @Test
    @Order(1)
    @DisplayName("1. ??????????????? \"server\", \"receiver\" ??? ??????.")
    public void playerCheck() {
        assertAll(
            () -> assertEquals(server.type(), SERVER),
            () -> assertEquals(receiver.type(), RECEIVER)
        );
    }

    @Test
    @Order(2)
    @DisplayName("2. ??? 4??? ????????? ????????????, ??????????????? 2??? ?????? ??? ?????? ??????????????? ???????????? ??????")
    public void scoreCondition() {
        // ????????? ??????????????? ??????????????? ?????????
        TennisGameSimulation.play(server, receiver);

        // ????????? ?????? ?????? ??????
        Map<TennisGameAwards, Player> awards = TennisGameService.awards(server, receiver);
        System.out.println("server score: " + server.read());
        System.out.println("receiver score: " + receiver.read());
        System.out.println("Victory Player: " + awards.get(WINNER));
        System.out.println("Loser Player: " + awards.get(LOSER));

        assertAll(
            // ????????? ????????? ??????????????? 2??? ???????????? ??????
            () -> assertTrue(awards.get(WINNER).read() - awards.get(LOSER).read() >= BASE_COMPARE_SCORE.getValue()),
            // ????????? ??? 4???????????????
            () -> assertTrue(awards.get(WINNER).read() >= BASE_VICTORY_SCORE.getValue())
        );
    }

    @Test
    @Order(3)
    @DisplayName("3. ???????????? ???????????? 0????????? 3????????? \"love(0 -> 0)\", \"fifteen(1 -> 15)\", \"thirty(2 -> 30)\", \"forty(3 -> 40)\" ??? ?????????.")
    public void scoreDisplayTest() {
        // ex) love all(0:0), fifteen love(1:0), forty thirty(3:2)...

        // 0 ~ 3????????? ????????? ?????? ????????? ?????? Display ????????? ??????
        IntStream.range(0, 4).forEach(serverNum -> {
            IntStream.range(0, 4).forEach(receiverNum -> {
                if (server.read() == 0 && receiver.read() == 0) {
                    assertEquals("love all(0:0)", TennisGameService.sumScoreDisplay(server, receiver));
                } else if (server.read() == 1 && receiver.read() == 0) {
                    assertEquals("fifteen love(1:0)", TennisGameService.sumScoreDisplay(server, receiver));
                } else if (server.read() == 3 && receiver.read() == 2) {
                    assertEquals("forty thirty(3:2)", TennisGameService.sumScoreDisplay(server, receiver));
                }
                receiver.update(1);
            });
            server.update(1);
        });
    }

    @Test
    @Order(4)
    @DisplayName("4. ?????? 3?????? ???????????? ????????? ????????? ??????, ????????? \"deuce\" ??????.")
    public void deuceTest() {
        // 3:3??? true
        server.update(3);
        receiver.update(3);
        assertTrue(TennisGameService.deuceVerification(server, receiver));
        // 3:4??? false
        receiver.update(1);
        assertFalse(TennisGameService.deuceVerification(server, receiver));
        // 4:4??? true
        server.update(1);
        assertTrue(TennisGameService.deuceVerification(server, receiver));
    }

    @Test
    @Order(5)
    @DisplayName("5. \"deuce\" ??? ??? ??? ?????? ????????? ???????????? ???????????? ?????????????????? \"ad(advantage)\" ??????.")
    public void deuceAdvantageTest() {
        // ex) ad-in (server??? deuce ???????????? ??????), ad-out (receiver??? deuce ???????????? ??????)
        // 3:3
        server.update(3);
        receiver.update(3);
        assertEquals(DEUCE, TennisGameRepository.findAdvantage());

        // 3:4
        receiver.update(1);
        assertEquals(AD_OUT, TennisGameRepository.findAdvantage());

        // 3:5
        receiver.update(1);
        assertEquals(NONE, TennisGameRepository.findAdvantage());

        // 4:5
        server.update(1);
        assertEquals(NONE, TennisGameRepository.findAdvantage());

        // 5:5
        server.update(1);
        assertEquals(DEUCE, TennisGameRepository.findAdvantage());

        // 6:5
        server.update(1);
        assertEquals(AD_IN, TennisGameRepository.findAdvantage());

        // 7:5
        server.update(1);
        assertEquals(NONE, TennisGameRepository.findAdvantage());
    }

    @Test
    public void streamTest() {
        Player server = Server.of();
        Player receiver = Receiver.of();
        Player[] players = {server, receiver};
        System.out.println(Arrays.stream(players).map(Player::type).toList().containsAll(List.of(SERVER, RECEIVER)));

        // Object[] objects = Arrays.stream(players).map(Player::type)
    }

}