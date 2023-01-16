package com.fastcampus.tennistdd.practice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TennisGameAnnouncingPresenterTest {

    @Test
    void case_deuce() {
        // system under test
        final TennisGameAnnouncingPresenter sut = new TennisGameAnnouncingPresenter();

        sut.present(new PresentTennisGameRequest(-1, -1, null));
    }

    @Nested
    class PresentTennisGameRequestTest {

        @Test
        void ctor_illegal_args() {
            assertThatThrownBy(() -> new PresentTennisGameRequest(-1, 0, TennisGameStatus.STARTED))
                .isExactlyInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> new PresentTennisGameRequest(0, -1, TennisGameStatus.STARTED))
                .isExactlyInstanceOf(IllegalArgumentException.class);        }
    }

    private static class TennisGameAnnouncingPresenter {
        public String present(final PresentTennisGameRequest request) {
            return null;
        }
    }
}
