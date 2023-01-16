package com.fastcampus.tennistdd.practice;

import org.junit.jupiter.api.Test;

public class TennisGameAnnouncingPresenterTest {

    @Test
    void case_deuce() {
        // system under test
        final TennisGameAnnouncingPresenter sut = new TennisGameAnnouncingPresenter();

        sut.present(new PresentTennisGameRequest(-1, -1, null));
    }

    private static class TennisGameAnnouncingPresenter {
        public String present(final PresentTennisGameRequest request) {
            return null;
        }
    }
}
