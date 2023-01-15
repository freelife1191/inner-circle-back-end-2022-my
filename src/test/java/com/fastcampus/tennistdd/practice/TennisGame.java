package com.fastcampus.tennistdd.practice;

class TennisGame {
    private int serverPoints;
    private int receiverPoints;

    public void serverScores() {
        validateGameIsRunning();
        ++serverPoints;
    }

    public void receiverScores() {
        validateGameIsRunning();
        ++receiverPoints;
    }

    private void validateGameIsRunning() {
        if (isFinished()) throw new IllegalStateException();
    }

    public int serverPoints() {
        return serverPoints;
    }

    public int receiverPoints() {
        return receiverPoints;
    }

    public TennisGameStatus status() {
        if (isFinished()) return TennisGameStatus.FINISHED;
        if (isDeuce()) return TennisGameStatus.DEUCE;
        if (3 <= Math.min(serverPoints, receiverPoints) && 1 == serverPoints - receiverPoints)
            return TennisGameStatus.ADVANTAGE_IN;
        return TennisGameStatus.STARTED;
    }

    private boolean isDeuce() {
        return 3 <= Math.min(serverPoints, receiverPoints) && serverPoints == receiverPoints;
    }

    private boolean isFinished() {
        return 2 <= Math.abs(serverPoints - receiverPoints) && 4 <= Math.max(serverPoints, receiverPoints);
    }
}
