package com.fastcampus.tennistdd.myeongseop;

public class TennisGame {
    private static final int SET_POINT = 4;
    private static final int MATCH_POINT = 7;
    private static final int DIFFERENCE_IN_POINT = 2;
    private static final int DEUCE_CONDITION_POINT = 3;
    private static final String SCORE_LOVE = "love";
    private static final String SCORE_FIFTEEN = "fifteen";
    private static final String SCORE_THIRTY = "thirty";
    private static final String SCORE_FORTY = "forty";
    private static final String SCORE_DEUCE = "deuce";
    private static final String ADVANTAGE_IN = "ad-in";
    private static final String ADVANTAGE_OUT = "ad-out";

    private final Player server;
    private final Player receiver;

    private String advantage;

    public TennisGame(Player server, Player receiver) {
        if (isNotCorrectPlayers(server, receiver)) {
            throw new IllegalArgumentException("테니스 게임을 생성 할 수 없는 조건의 점수를 가진 플레이어입니다.");
        }
        this.server = server;
        this.receiver = receiver;
    }

    public Winner winner() {
        if (isWinTheGameBy(server, receiver)) {
            return Winner.SERVER;
        }
        if (isWinTheGameBy(receiver, server)) {
            return Winner.RECEIVER;
        }
        return Winner.NONE;
    }

    public String getScore() {
        if (isDeuce()) {
            return SCORE_DEUCE;
        }

        String serverScore = getScore(server.getPoint());
        if (isSamePoint()) {
            return String.format("%s all", serverScore);
        }

        return String.format("%s %s", serverScore, getScore(receiver.getPoint()));
    }

    public boolean isDeuce() {
        return isSamePoint() && isGreaterThenOrEquals3Point();
    }

    public void gameByServer() {
        if (isDeuce()) {
            advantage = ADVANTAGE_IN;
        }
        server.increase();
    }

    public void gameByReceiver() {
        if (isDeuce()) {
            advantage = ADVANTAGE_OUT;
        }
        receiver.increase();
    }

    public boolean isAdvantageIn() {
        return ADVANTAGE_IN.equals(advantage);
    }

    public boolean isAdvantageOut() {
        return ADVANTAGE_OUT.equals(advantage);
    }

    private boolean isNotCorrectPlayers(Player server, Player receiver) {
        return isAllowRolePlayers(server, receiver) || isAllowRolePlayers(receiver, server);
    }

    private boolean isAllowRolePlayers(Player player, Player other) {
        return isGreaterThenOrEqualsSetPoint(player.getPoint()) && player.difference(other) > DIFFERENCE_IN_POINT;
    }

    private boolean isWinTheGameBy(Player player, Player other) {
        return isWinCondition(player, other) || isMatchPoint(player.getPoint());
    }

    private boolean isWinCondition(Player player, Player other) {
        return isGreaterThenOrEqualsSetPoint(player.getPoint()) && player.difference(other) >= DIFFERENCE_IN_POINT;
    }

    private boolean isGreaterThenOrEqualsSetPoint(int point) {
        return point >= SET_POINT;
    }

    private boolean isMatchPoint(int point) {
        return point == MATCH_POINT;
    }

    private String getScore(int point) {
        switch (point) {
            case 0:
                return SCORE_LOVE;
            case 1:
                return SCORE_FIFTEEN;
            case 2:
                return SCORE_THIRTY;
            case 3:
                return SCORE_FORTY;
            default:
                return String.valueOf(point);
        }
    }

    private boolean isSamePoint() {
        return server.getPoint() == receiver.getPoint();
    }

    private boolean isGreaterThenOrEquals3Point() {
        return server.getPoint() >= DEUCE_CONDITION_POINT && server.getPoint() >= DEUCE_CONDITION_POINT;
    }
}
