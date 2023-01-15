package com.fastcampus.tennistdd.jake;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public final class PresentTennisGameRequest {
    private final int serverPoints;
    private final int receiverPoints;
    private final TennisGameStatus status;

    public PresentTennisGameRequest(final int serverPoints, final int receiverPoints, final TennisGameStatus status) {
        Assert.isTrue(0 <= serverPoints, "serverPoints must not be a negative");
        Assert.isTrue(0 <= receiverPoints, "receiverPoints must not be a negative");
        Assert.notNull(status, "status must not be null");
        this.serverPoints = serverPoints;
        this.receiverPoints = receiverPoints;
        this.status = status;
    }
}