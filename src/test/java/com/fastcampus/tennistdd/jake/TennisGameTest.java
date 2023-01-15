package com.fastcampus.tennistdd.jake;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
 * 3. 테니스의 스코어는 0점에서 3점까지 "love(0 -> 0)", "fifteen(1 -> 15)", "thirty(2 -> 30)", "forty(3 -> 40)" 로 나타냄. ex) love all(0:0), fifteen love(1:0), forty thirty(3:2)...
 * <p>
 * 4. 최소 3점을 득점하고 점수가 동점인 경우, 점수는 "deuce" 이다.
 * <p>
 * 5. "deuce" 일 때 한 쪽이 점수를 득점하면 리드하는 플레이어에게 "ad(advantage)" 이다. ex) ad-in (server가 deuce 상황에서 득점), ad-out (receiver가 deuce 상황에서 득점)
 * <p>
 * 클래스에는 최소 두 개의 메서드가 있어야 한다. 스코어 조회(Read), 그리고 득점(Update). 코딩테스트가 아니므로, 클래스 디자인은 자유롭게!
 */
class TennisGameTest {

    private TennisGame createAndPlayUntil(final int serverPoints, final int receiversPoints) {
        final TennisGame game = new TennisGame();
        for (int i = 0; i < serverPoints; i++)
            game.serverScores();
        for (int i = 0; i < receiversPoints; i++)
            game.receiverScores();
        return game;
    }

    @Test
    @DisplayName("생성자가 기대하는 초기 상태를 가진 객체를 반환한다.")
    void case0() {
        final TennisGame newlyCreatedGame = new TennisGame();

        assertThat(newlyCreatedGame.getServerPoints()).isZero();
        assertThat(newlyCreatedGame.getReceiverPoints()).isZero();
        assertThat(newlyCreatedGame.status()).isEqualTo(TennisGameStatus.STARTED);
    }

    @Test
    @DisplayName("serverScores, receiverScores 메서드가 기대하는 업데이트를 일으킨다.")
    void case1() {
        final TennisGame game = new TennisGame();

        game.serverScores();
        game.receiverScores();
        game.receiverScores();

        assertThat(game.getServerPoints()).isEqualTo(1);
        assertThat(game.getReceiverPoints()).isEqualTo(2);
    }

    @Test
    @DisplayName("한 플레이어가 2점 이상의 점수차로 4점 이상 득점하면 게임이 끝난다.")
    void case2() {
        final TennisGame game = createAndPlayUntil(3, 2);

        game.serverScores();

        assertThat(game.status()).isEqualTo(TennisGameStatus.FINISHED);
    }

    @Test
    @DisplayName("게임이 이미 끝났을 때 점수를 추가하면 기대하는 예외를 던진다.")
    void case3() {
        final TennisGame finishedGame = createAndPlayUntil(4, 0);

        assertThatThrownBy(finishedGame::serverScores)
                .isExactlyInstanceOf(IllegalStateException.class);
        assertThatThrownBy(finishedGame::receiverScores)
                .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("3 포인트 이상에서 동점이면 DEUCE 이다.")
    void case4() {
        final TennisGame game = createAndPlayUntil(3, 2);

        game.receiverScores();

        assertThat(game.status()).isEqualTo(TennisGameStatus.DEUCE);
    }

    @Test
    @DisplayName("DEUCE 일 때 득점하면 ADVANTAGE 이다")
    void case5() {
        final TennisGame deucedGame = createAndPlayUntil(3, 3);

        deucedGame.serverScores();

        assertThat(deucedGame.status()).isEqualTo(TennisGameStatus.ADVANTAGE);
    }

}
