package com.fastcampus.tennistdd.jake;

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

    public int getServerPoints() {
        return serverPoints;
    }

    public int getReceiverPoints() {
        return receiverPoints;
    }

    public TennisGameStatus status() {
        if (isFinished()) return TennisGameStatus.FINISHED;
        if (isDeuce()) return TennisGameStatus.DEUCE;
        if (isAdvantage()) return TennisGameStatus.ADVANTAGE;
        return TennisGameStatus.STARTED;
    }

    private boolean isFinished() {
        return 2 <= Math.abs(serverPoints - receiverPoints) && 4 <= Math.max(serverPoints, receiverPoints);
    }

    private boolean isDeuce() {
        return isGreaterThanDeucePoints() && serverPoints == receiverPoints;
    }

    private boolean isAdvantage() {
        return isGreaterThanDeucePoints() && 1 == Math.abs(serverPoints - receiverPoints);
    }

    private boolean isGreaterThanDeucePoints() {
        return 3 <= Math.min(serverPoints, receiverPoints);
    }
}
