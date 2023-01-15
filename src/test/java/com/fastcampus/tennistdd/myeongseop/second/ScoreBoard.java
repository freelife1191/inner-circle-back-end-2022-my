package com.fastcampus.tennistdd.myeongseop.second;

class ScoreBoard {
    private static final String LOVE = "love";
    private static final String FIFTEEN = "fifteen";
    private static final String THIRTY = "thirty";
    private static final String FORTY = "forty";
    private static final String DEUCE = "deuce";

    private Player server;
    private Player receiver;

    protected ScoreBoard(TennisGame tennisGame) {
        this.server = tennisGame.getServer();
        this.receiver = tennisGame.getReceiver();
    }

    public String viewScore() {
        if (server.isDeuce(receiver)) {
            return DEUCE;
        }

        if (server.getScore().equals(receiver.getScore())) {
            return String.format("%s all", playerScore(server.getScore().getPoint()));
        }

        return String.format("%s %s", playerScore(server.getScore().getPoint()), playerScore(receiver.getScore().getPoint()));
    }

    private String playerScore(int point) {
        switch (point) {
            case 0:
                return LOVE;
            case 1:
                return FIFTEEN;
            case 2:
                return THIRTY;
            case 3:
                return FORTY;
            default:
                return String.valueOf(point);
        }
    }
}
