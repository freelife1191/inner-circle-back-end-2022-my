package com.fastcampus.tennistdd.myeongseop;

public class AlreadyMaxPointException extends RuntimeException {
    public AlreadyMaxPointException() {
        super("이미 최대 점수에 도달했습니다. 더 이상 득점할 수 없습니다.");
    }
}
