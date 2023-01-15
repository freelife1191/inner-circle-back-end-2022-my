package com.fastcampus.tennistdd.myeongseop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Tennis Game
 * <p>
 * Rules:
 * <p>
 * 1. 플레이어는 "server", "receiver" 로 나뉨.
 * <p>
 * 2. 총 4점 이상을 획득하고, 상대방보다 2점 이상 더 많은 플레이어가 게임에서 승리.
 * <p>
 * 3. 테니스의 스코어는 0점에서 3점까지 "love", "fifteen", "thirty", "forty" 로 나타냄. ex) love all(0:0), fifteen love(1:0), forty all(3:3)...
 * <p>
 * 4. 최소 3점을 득점하고 점수가 동점인 경우, 점수는 "deuce" 이다.
 * <p>
 * 5. "deuce" 일 때 한 쪽이 점수를 득점하면 리드하는 플레이어에게 "ad(advantage)" 이다. ex) ad-in (server가 deuce 상황에서 득점), ad-out (receiver가 deuce 상황에서 득점)
 * <p>
 * 6. 점수가 6:6 이면, 7점을 먼저 획득하는 사람이 이긴다. (tie break)
 * <p>
 * 클래스에는 최소 두 개의 메서드가 있어야 한다. 스코어 조회(Read), 그리고 득점(Update). 코딩테스트가 아니므로, 클래스 디자인은 자유롭게!
 */
class TennisGameTest {

    @DisplayName("절대 나올 수 없는 점수로 테니스 게임을 생성하면 예외가 발생한다.")
    @Test
    void notGenerateTennisGame() {
        assertAll(
                () -> assertThatThrownBy(() -> generateTennisGame(6, 0))
                    .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> generateTennisGame(3, 6))
                    .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> generateTennisGame(100, 0))
                    .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("총 4점 이상을 획득하고, 상대방보다 2점 이상 더 많은 플레이어가 게임에서 승리.")
    @Test
    void winPlayer() {
        assertAll(
                () -> assertTennisGameWinner(0, 0, Winner.NONE),
                () -> assertTennisGameWinner(4, 2, Winner.SERVER),
                () -> assertTennisGameWinner(2, 4, Winner.RECEIVER)
        );
    }

    @DisplayName("총 4점 이상을 획득하지 못하고, 상대방보다 2점 이상 더 많으면 승리자가 없다.")
    @Test
    void noneWinnerForNonGreaterThenFour() {
        assertAll(
                () -> assertTennisGameWinner(2, 0, Winner.NONE),
                () -> assertTennisGameWinner(0, 2, Winner.NONE)
        );
    }

    @DisplayName("총 4점 이상을 획득하고, 상대방보다 1점 많으면 승리자가 없다.")
    @Test
    void noneWinnerForMorePointOne() {
        assertAll(
                () -> assertTennisGameWinner(5, 4, Winner.NONE),
                () -> assertTennisGameWinner(4, 5, Winner.NONE)
        );
    }

    @DisplayName("테니스의 스코어는 0점에서 3점까지 love, fifteen, thirty, forty 로 나타낸다.")
    @Test
    void getScoreOfGame() {
        assertAll(
                () -> assertTennisGameScore(0, 0, "love all"),
                () -> assertTennisGameScore(1, 0, "fifteen love"),
                () -> assertTennisGameScore(0, 1, "love fifteen"),
                () -> assertTennisGameScore(1, 1, "fifteen all"),
                () -> assertTennisGameScore(2, 0, "thirty love"),
                () -> assertTennisGameScore(0, 2, "love thirty"),
                () -> assertTennisGameScore(2, 2, "thirty all"),
                () -> assertTennisGameScore(3, 2, "forty thirty"),
                () -> assertTennisGameScore(2, 3, "thirty forty")
        );
    }

    @DisplayName("테니스의 스코어는 4점 이상은 숫자 점수로 나타낸다.")
    @Test
    void getDigitScoreGreaterThen4Point() {
        assertAll(
                () -> assertTennisGameScore(4, 5, "4 5"),
                () -> assertTennisGameScore(5, 6, "5 6"),
                () -> assertTennisGameScore(4, 6, "4 6")
        );
    }

    @DisplayName("최소 3점을 득점하고 점수가 동점인 경우, 점수는 deuce 이다.")
    @Test
    void deuceWhenGet3PointAndSamePoint() {
        assertAll(
                () -> assertTennisGameDeuce(3, 3, true),
                () -> assertTennisGameDeuce(4, 4, true),
                () -> assertTennisGameDeuce(5, 5, true),
                () -> assertTennisGameDeuce(6, 6, true)
        );
    }

    @DisplayName("최소 3점을 득점하고 점수가 동점이 아니면, 점수는 deuce가 아니다.")
    @Test
    void deuceWhenGet3Point() {
        assertAll(
                () -> assertTennisGameDeuce(3, 4, false),
                () -> assertTennisGameDeuce(4, 5, false),
                () -> assertTennisGameDeuce(5, 6, false)
        );
    }

    @DisplayName("3점을 득점하지 못하고 점수가 동점이면, 점수는 deuce가 아니다.")
    @Test
    void deuceWhenSamePoint() {
        assertAll(
                () -> assertTennisGameDeuce(1, 1, false),
                () -> assertTennisGameDeuce(2, 2, false)
        );
    }

    @DisplayName("듀스일 때 서버 플레이어가 점수를 득점하면 advantage-in이다.")
    @Test
    void advantageInWhenDeuceAndServerGetGame() {
        assertAdvantageIn(generateTennisGame(3, 3));
    }

    @DisplayName("듀스일 때 서버 플레이어가 점수를 득점하면 advantage-in이고 한번 더 득점하면 승리한다.")
    @Test
    void advantageInWhenDeuceAndServerGetGameMore() {
        TennisGame tennisGame = generateTennisGame(3, 3);
        assertAll(
                () -> assertAdvantageIn(tennisGame),
                () -> assertAdvantageIn(tennisGame),
                () -> assertThat(tennisGame.winner()).isEqualTo(Winner.SERVER)
        );
    }

    @DisplayName("듀스일 때 리시버 플레이어가 점수를 득점하면 advantage-out이다.")
    @Test
    void advantageOutWhenDeuceAndReceiverGetGame() {
        assertAdvantageOut(generateTennisGame(3, 3));
    }

    @DisplayName("듀스일 때 리시버 플레이어가 점수를 득점하면 advantage-out이고 한번 더 득점하면 승리한다.")
    @Test
    void advantageOutWhenDeuceAndReceiverGetGameMore() {
        TennisGame tennisGame = generateTennisGame(3, 3);
        assertAll(
                () -> assertAdvantageOut(tennisGame),
                () -> assertAdvantageOut(tennisGame),
                () -> assertThat(tennisGame.winner()).isEqualTo(Winner.RECEIVER)
        );
    }

    @DisplayName("점수가 6:6인 상황에서 서버가 득점하면 우승한다.")
    @Test
    void winServerWhenGameByServerBefore6AllDeuce() {
        TennisGame tennisGame = generateTennisGame(6, 6);
        tennisGame.gameByServer();
        assertThat(tennisGame.winner()).isEqualTo(Winner.SERVER);
    }

    @DisplayName("점수가 6:6인 상황에서 리시버가 득점하면 우승한다.")
    @Test
    void winServerWhenGameByReceiverBefore6AllDeuce() {
        TennisGame tennisGame = generateTennisGame(6, 6);
        tennisGame.gameByReceiver();
        assertThat(tennisGame.winner()).isEqualTo(Winner.RECEIVER);
    }

    private void assertAdvantageOut(TennisGame tennisGame) {
        tennisGame.gameByReceiver();
        assertThat(tennisGame.isAdvantageOut()).isTrue();
    }

    private void assertAdvantageIn(TennisGame tennisGame) {
        tennisGame.gameByServer();
        assertThat(tennisGame.isAdvantageIn()).isTrue();
    }

    private void assertTennisGameDeuce(int serverPoint, int receiverPoint, boolean expected) {
        TennisGame tennisGame = generateTennisGame(serverPoint, receiverPoint);
        assertThat(tennisGame.isDeuce()).isEqualTo(expected);
        if (tennisGame.isDeuce()) {
            assertThat(tennisGame.getScore()).isEqualTo("deuce");
        }
    }

    private void assertTennisGameScore(int serverPoint, int receiverPoint, String expected) {
        assertThat(getScore(serverPoint, receiverPoint)).isEqualTo(expected);
    }

    private void assertTennisGameWinner(int serverPoint, int receiverPoint, Winner receiver) {
        assertThat(getWinner(serverPoint, receiverPoint)).isEqualTo(receiver);
    }

    private String getScore(int serverPoint, int receiverPoint) {
        return generateTennisGame(serverPoint, receiverPoint).getScore();
    }

    private Winner getWinner(int serverPoint, int receiverPoint) {
        return generateTennisGame(serverPoint, receiverPoint).winner();
    }

    private TennisGame generateTennisGame(int serverPoint, int receiverPoint) {
        return new TennisGame(new Player(serverPoint), new Player(receiverPoint));
    }

}
