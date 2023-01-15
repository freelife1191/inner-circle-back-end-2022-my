package com.fastcampus.tennistdd.practice;

class TennisGame {
    private int serverPoints;
    private int receiverPoints;

    public void serverScores() {
        ++serverPoints;
    }

    public void receiverScores() {
        ++receiverPoints;
    }

    public int serverPoints() {
        return serverPoints;
    }

    public int receiverPoints() {
        return receiverPoints;
    }

    public TennisGameStatus status() {
        return TennisGameStatus.STARTED;
    }
}
