package com.fastcampus.tennistdd.practice;

class TennisGame {
    private int serverPoints;
    private int receiverPoints;

    public void serverScores() {
        if (isFinished()) throw new IllegalStateException();
        ++serverPoints;
    }

    public void receiverScores() {
        if (isFinished()) throw new IllegalStateException();
        ++receiverPoints;
    }

    public int serverPoints() {
        return serverPoints;
    }

    public int receiverPoints() {
        return receiverPoints;
    }

    public TennisGameStatus status() {
        if (isFinished()) return TennisGameStatus.FINISHED;
        return TennisGameStatus.STARTED;
    }

    private boolean isFinished() {
        return 2 <= Math.abs(serverPoints - receiverPoints) && 4 <= Math.max(serverPoints, receiverPoints);
    }
}
