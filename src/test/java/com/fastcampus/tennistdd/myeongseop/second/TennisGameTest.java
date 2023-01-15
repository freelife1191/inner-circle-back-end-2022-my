package com.fastcampus.tennistdd.myeongseop.second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tennis Game
 * <p>
 * Rules:
 * <p>
 * 1. 플레이어는 "server", "receiver" 로 나뉨.
 * <p>
 * 2. 총 4점 이상을 획득하고, 상대방보다 2점 이상 더 많은 플레이어가 게임에서 승리.
 * <p>
 * 3. 테니스의 스코어는 0점에서 3점까지 "love", "fifteen", "thirty", "forty" 로 나타냄. ex) love all(0:0), fifteen love(1:0), forty thirty(3:2)...
 * <p>
 * 4. 최소 3점을 득점하고 점수가 동점인 경우, 점수는 "deuce" 이다.
 * <p>
 * 5. "deuce" 일 때 한 쪽이 점수를 득점하면 리드하는 플레이어에게 "ad(advantage)" 이다. ex) ad-in (server가 deuce 상황에서 득점), ad-out (receiver가 deuce 상황에서 득점)
 * <p>
 * 6. 점수가 6:6 이면, 7점을 먼저 획득하는 사람이 이긴다. (tie break)
 * <p>
 * 클래스에는 최소 두 개의 메서드가 있어야 한다. 스코어 조회(Read), 그리고 득점(Update). 코딩테스트가 아니므로, 클래스 디자인은 자유롭게!
 */
@DisplayName("테니스 게임 테스트")
class TennisGameTest {

    @DisplayName("테니스 게임 플레이어는 server, receiver 가 존재해야 한다.")
    @Test
    void givenTennisGame_whenServerAndReceiver_thenOk() {
        TennisGame tennisGame = new TennisGame(new Player(0), new Player(0));
        assertThat(tennisGame.getServer()).isNotNull();
        assertThat(tennisGame.getReceiver()).isNotNull();
    }

    @DisplayName("절대 나올 수 없는 점수로 게임을 만들면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"100:0", "3:6", "2:6", "7:7"}, delimiter = ':')
    void givenInvalidScore_whenConstructTennisGame_thenException(int serverPoint, int receiverPoint) {
        assertThatThrownBy(() -> new TennisGame(new Player(serverPoint), new Player(receiverPoint)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미 승리자가 결정되는 점수도 테니스 게임을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"7:6", "7:5", "5:3", "4:6"}, delimiter = ':')
    void givenInDecidedScore_whenConstructTennisGame_thenOk(int serverPoint, int receiverPoint) {
        TennisGame tennisGame = new TennisGame(new Player(serverPoint), new Player(receiverPoint));
        assertThat(tennisGame).isNotNull();
    }

    @DisplayName("서버가 4점 이상 획득하고 리시버보다 2점 이상 많다면 승리한다.")
    @ParameterizedTest
    @CsvSource(value = {"3:2", "4:3", "5:4", "6:5"}, delimiter = ':')
    void givenServerPointIs4AndGreaterThen2OfReceiverPoint_whenWinnerIsServer_thenOk(int serverPoint, int receiverPoint) {
        TennisGame tennisGame = new TennisGame(new Player(serverPoint), new Player(receiverPoint));
        Winner result = tennisGame.gameByServer();
        assertThat(result).isEqualTo(Winner.SERVER);
    }

    @DisplayName("서버가 4점 이상 획득하지 못 하고 리시버보다 2점 이상 많다면 승리하지 못 한다.")
    @ParameterizedTest
    @CsvSource(value = {"0:0", "1:0", "1:1", "2:0", "2:1", "2:2"}, delimiter = ':')
    void givenServerPointLessThen4AndGreaterThen2OfReceiverPoint_whenWinnerIsNone_thenOk(int serverPoint, int receiverPoint) {
        TennisGame tennisGame = new TennisGame(new Player(serverPoint), new Player(receiverPoint));
        Winner result = tennisGame.gameByServer();
        assertThat(result).isEqualTo(Winner.NONE);
    }

    @DisplayName("리시버가 4점 이상 획득하고 서버보다 2점 이상 많다면 승리한다.")
    @ParameterizedTest
    @CsvSource(value = {"2:3", "3:4", "4:5", "5:6"}, delimiter = ':')
    void givenReceiverPointIs4AndGreaterThen2OfServerPoint_whenWinnerIsReceiver_thenOk(int serverPoint, int receiverPoint) {
        TennisGame tennisGame = new TennisGame(new Player(serverPoint), new Player(receiverPoint));
        Winner result = tennisGame.gameByReceiver();
        assertThat(result).isEqualTo(Winner.RECEIVER);
    }

    @DisplayName("리시버가 4점 이상 획득하지 못 하고 서버보다 2점 이상 많다면 승리하지 못 한다.")
    @ParameterizedTest
    @CsvSource(value = {"0:0", "0:1", "1:1", "0:2", "1:2", "2:2"}, delimiter = ':')
    void givenReceiverPointLessThen4AndGreaterThen2OfServerPoint_whenWinnerIsNone_thenOk(int serverPoint, int receiverPoint) {
        TennisGame tennisGame = new TennisGame(new Player(serverPoint), new Player(receiverPoint));
        Winner result = tennisGame.gameByReceiver();
        assertThat(result).isEqualTo(Winner.NONE);
    }

    @DisplayName("deuce 일 때 서버가 점수를 득점하면 ad-in 이다.")
    @ParameterizedTest
    @CsvSource(value = {"3:3", "4:4", "5:5"}, delimiter = ':')
    void givenIsDeuceAfterGameByServer_whenAdvantageIn_thenOk() {
        TennisGame tennisGame = new TennisGame(new Player(3), new Player(3));
        Winner result = tennisGame.gameByServer();
        assertThat(result).isEqualTo(Winner.NONE);
        assertThat(tennisGame.isAdvantageIn()).isTrue();
    }

    @DisplayName("deuce 일 때 리시버가 점수를 득점하면 ad-out 이다.")
    @ParameterizedTest
    @CsvSource(value = {"3:3", "4:4", "5:5"}, delimiter = ':')
    void givenIsDeuceAfterGameByReceiver_whenAdvantageOut_thenOk() {
        TennisGame tennisGame = new TennisGame(new Player(3), new Player(3));
        Winner result = tennisGame.gameByReceiver();
        assertThat(result).isEqualTo(Winner.NONE);
        assertThat(tennisGame.isAdvantageOut()).isTrue();
    }

    @DisplayName("점수가 6:6 일 때, 서버가 득점하면 승리한다.")
    @Test
    void givenDeuce6PointAndGameByServer_whenWinnerIsServer_thenOk() {
        TennisGame tennisGame = new TennisGame(new Player(6), new Player(6));
        Winner winner = tennisGame.gameByServer();
        assertThat(winner).isEqualTo(Winner.SERVER);
    }

    @DisplayName("점수가 6:6 일 때, 리시버가 득점하면 승리한다.")
    @Test
    void givenDeuce6PointAndGameByReceiver_whenWinnerIsReceiver_thenOk() {
        TennisGame tennisGame = new TennisGame(new Player(6), new Player(6));
        Winner winner = tennisGame.gameByReceiver();
        assertThat(winner).isEqualTo(Winner.RECEIVER);
    }

    @DisplayName("테니스 게임은 점수판을 반환할 수 있다.")
    @Test
    void givenTennisGame_whenScoreBoard_thenOk() {
        TennisGame tennisGame = new TennisGame(new Player(0), new Player(0));
        ScoreBoard scoreBoard = tennisGame.toScoreBoard();
        assertThat(scoreBoard).isNotNull();
        assertThat(scoreBoard).isInstanceOf(ScoreBoard.class);
    }
}
