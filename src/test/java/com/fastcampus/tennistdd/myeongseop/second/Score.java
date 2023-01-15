package com.fastcampus.tennistdd.myeongseop.second;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
class Score {
    private static final int MIN_POINT = 0;
    private static final int MAX_POINT = 7;
    private static final String CONSTRUCT_INVALID_EXCEPTION_MESSAGE = "잘못된 점수를 입력하였습니다. 0~7 사이의 점수를 입력해주세요.";
    private static final String ALREADY_MAX_POINT_EXCEPTION_MESSAGE = "더 이상 득점할 수 없습니다.";

    private int point;

    protected Score(int point) {
        if (isNotCorrectPoint(point)) {
            throw new IllegalArgumentException(CONSTRUCT_INVALID_EXCEPTION_MESSAGE);
        }
        this.point = point;
    }

    public void run() {
        if (isMaxPoint()) {
            throw new IllegalCallerException(ALREADY_MAX_POINT_EXCEPTION_MESSAGE);
        }
        this.point++;
    }

    public boolean isMaxPoint() {
        return this.isGreaterThen(MAX_POINT);
    }

    public int difference(Score other) {
        return Math.abs(this.point - other.point);
    }

    public boolean isGreaterThen(int point) {
        return this.point >= point;
    }

    private boolean isNotCorrectPoint(int point) {
        return !(point >= MIN_POINT && point <= MAX_POINT);
    }
}
