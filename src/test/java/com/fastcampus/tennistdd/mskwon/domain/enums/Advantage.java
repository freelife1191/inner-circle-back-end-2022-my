package com.fastcampus.tennistdd.mskwon.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Advantage {
    DEUCE("deuce"),
    AD_IN("ad-in"),
    AD_OUT("ad-out"),
    NONE("none");

    private final String name;

    public static Advantage reverse(Advantage advantage) {
        return switch (advantage) {
            case AD_IN -> AD_OUT;
            case AD_OUT -> AD_IN;
            default -> advantage;
        };
    }
}
