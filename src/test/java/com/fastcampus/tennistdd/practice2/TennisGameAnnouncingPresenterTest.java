package com.fastcampus.tennistdd.practice2;

import org.junit.jupiter.api.Test;

public class TennisGameAnnouncingPresenterTest {

    @Test
    void case_deuce() {
        // system under test
        final TennisGameAnnouncingPresenter sut = new TennisGameAnnouncingPresenter();

        sut.present(-1, -1, null);
    }

    private static class TennisGameAnnouncingPresenter {
    }
}
