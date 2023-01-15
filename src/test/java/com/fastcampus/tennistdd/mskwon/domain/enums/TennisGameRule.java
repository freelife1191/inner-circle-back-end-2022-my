package com.fastcampus.tennistdd.mskwon.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TennisGameRule {
    BASE_VICTORY_SCORE(4),
    BASE_COMPARE_SCORE(2),
    BASE_DEUCE_SCORE(3);

    private final int value;

}
