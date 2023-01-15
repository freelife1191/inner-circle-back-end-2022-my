package com.fastcampus.tennistdd.myeongseop;

import lombok.Getter;

@Getter
public class Player {
    protected static final String INITIALIZE_POINT_EXCEPTION_MESSAGE = "플레이어의 점수는 0~7 사이의 숫자 점수로 초기화 해야 합니다.";
    private static final int MIN_POINT = 0;
    private static final int MAX_POINT = 7;
    private int point;

    public Player(int point) {
        if (isNotCorrectPoint(point)) {
            throw new IllegalArgumentException(INITIALIZE_POINT_EXCEPTION_MESSAGE);
        }
        this.point = point;
    }

    private boolean isNotCorrectPoint(int point) {
        return point < MIN_POINT || point > MAX_POINT;
    }

    public void increase() {
        if (point == MAX_POINT) {
            throw new AlreadyMaxPointException();
        }
        point++;
    }

    public int difference(Player other) {
        return Math.abs(this.point - other.point);
    }
}
