package com.fastcampus.tennistdd.myeongseop.second;

import lombok.Getter;

@Getter
public class TennisGame {
    private static final String CONSTRUCT_INVALID_EXCEPTION_MESSAGE = "잘못된 게임 점수를 가진 플레이어들 입니다.";

    private Player server;
    private Player receiver;

    public TennisGame(Player server, Player receiver) {
        if (isInvalidPlayers(server, receiver)) {
            throw new IllegalArgumentException(CONSTRUCT_INVALID_EXCEPTION_MESSAGE);
        }

        this.server = server;
        this.receiver = receiver;
    }

    public Winner gameByServer() {
        this.server.matchUp(this.receiver);

        if (this.server.isGameBy(this.receiver)) {
            return Winner.SERVER;
        }
        return Winner.NONE;
    }

    public Winner gameByReceiver() {
        this.receiver.matchUp(this.server);

        if (this.receiver.isGameBy(this.server)) {
            return Winner.RECEIVER;
        }
        return Winner.NONE;
    }

    public boolean isAdvantageIn() {
        return this.server.isAdvantage();
    }

    public boolean isAdvantageOut() {
        return this.receiver.isAdvantage();
    }

    public ScoreBoard toScoreBoard() {
        return new ScoreBoard(this);
    }

    private boolean isInvalidPlayers(Player server, Player receiver) {
        return (server.isGamePoint() || receiver.isGamePoint()) && server.isGreaterThenSetPoint(receiver)
                || (server.isMaxPoint() && receiver.isMaxPoint());
    }

}
