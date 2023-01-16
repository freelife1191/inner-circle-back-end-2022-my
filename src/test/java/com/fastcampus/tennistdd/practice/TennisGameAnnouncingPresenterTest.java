package com.fastcampus.tennistdd.practice;

import org.junit.jupiter.api.Test;

public class TennisGameAnnouncingPresenterTest {

    @Test
    void case_deuce() {
        // system under test
        final TennisGameAnnouncingPresenter sut = new TennisGameAnnouncingPresenter();

        sut.present(-1, -1, null);
    }

    private static class TennisGameAnnouncingPresenter {
        public String present(final int serverPoints, final int receiverPoints, final TennisGameStatus status) {
            return null;
        }
    }
}
