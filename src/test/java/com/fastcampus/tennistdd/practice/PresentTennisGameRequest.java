package com.fastcampus.tennistdd.practice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PresentTennisGameRequest {
    private final int serverPoints;
    private final int receiverPoints;
    private final TennisGameStatus status;
}