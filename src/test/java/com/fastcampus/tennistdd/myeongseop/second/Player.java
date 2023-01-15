package com.fastcampus.tennistdd.myeongseop.second;

class Player {
    private static final int DEUCE_POINT = 3;
    private static final int SET_POINT = 2;
    private static final int GAME_POINT = 4;

    private Score score;
    private boolean advantage;

    protected Player(int point) {
        this.score = new Score(point);
    }

    public Score getScore() {
        return this.score;
    }

    public boolean isGameBy(Player other) {
        return (this.isGamePoint() && this.score.difference(other.score) == SET_POINT) || isMaxPoint();
    }

    public boolean isMaxPoint() {
        return this.score.isMaxPoint();
    }

    public boolean isGreaterThenSetPoint(Player other) {
        return this.score.difference(other.score) > SET_POINT;
    }

    public boolean isGamePoint() {
        return this.score.isGreaterThen(GAME_POINT);
    }

    public boolean isDeuce(Player other) {
        return this.score.isGreaterThen(DEUCE_POINT) && this.score.equals(other.score);
    }

    public void matchUp(Player other) {
        if (isDeuce(other)) {
            this.advantage = true;
            other.advantage = false;
        }
        this.score.run();
    }

    public boolean isAdvantage() {
        return this.advantage;
    }

}
