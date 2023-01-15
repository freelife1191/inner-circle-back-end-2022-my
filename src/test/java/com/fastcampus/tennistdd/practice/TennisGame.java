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
        if (2 <= Math.abs(serverPoints - receiverPoints) && 4 <= Math.max(serverPoints, receiverPoints))
            return TennisGameStatus.FINISHED;
        return TennisGameStatus.STARTED;
    }
}
