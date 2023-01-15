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

    public int getServerPoints() {
        return serverPoints;
    }

    public int getReceiverPoints() {
        return receiverPoints;
    }

    public TennisGameStatus status() {
        if (isFinished()) return TennisGameStatus.FINISHED;
        if (isDeuce()) return TennisGameStatus.DEUCE;
        if (isAdvantageIn()) return TennisGameStatus.ADVANTAGE_IN;
        if (isAdvantageOut()) return TennisGameStatus.ADVANTAGE_OUT;
        return TennisGameStatus.STARTED;
    }

    private boolean isFinished() {
        return 2 <= Math.abs(serverPoints - receiverPoints) && 4 <= Math.max(serverPoints, receiverPoints);
    }

    private boolean isDeuce() {
        return isGreaterThanDeucePoints() && serverPoints == receiverPoints;
    }

    private boolean isAdvantageIn() {
        return isGreaterThanDeucePoints() && 1 == serverPoints - receiverPoints;
    }

    private boolean isAdvantageOut() {
        return isGreaterThanDeucePoints() && 1 == receiverPoints - serverPoints;
    }

    private boolean isGreaterThanDeucePoints() {
        return 3 <= Math.min(serverPoints, receiverPoints);
    }
}
