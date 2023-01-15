package com.fastcampus.tennistdd.mskwon.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ScoreDisplay {

    LOVE("love", 0),
    FIFTEEN("fifteen", 1),
    THIRTY("thirty", 2),
    FORTY("forty", 3),
    NONE("none", -1);


    public final String name;
    public final int score;

    public static ScoreDisplay findByScore(int score) {
        return Arrays.stream(ScoreDisplay.values())
            .filter(display -> display.score == score)
            .findFirst()
            .orElse(NONE);
    }
}
