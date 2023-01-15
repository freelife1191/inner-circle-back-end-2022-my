package com.fastcampus.tennistdd.mskwon.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayerType {
    SERVER("server"),
    RECEIVER("receiver");

    private final String type;

    public static PlayerType reverse(PlayerType type) {
        return switch (type) {
            case SERVER -> RECEIVER;
            case RECEIVER -> SERVER;
        };
    }
}
