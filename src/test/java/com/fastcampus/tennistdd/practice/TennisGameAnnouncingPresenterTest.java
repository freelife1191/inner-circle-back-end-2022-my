package com.fastcampus.tennistdd.practice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TennisGameAnnouncingPresenterTest {

    @Test
    void case_deuce() {
        // system under test
        final TennisGameAnnouncingPresenter sut = new TennisGameAnnouncingPresenter();
        final PresentTennisGameRequest request = new PresentTennisGameRequest(3, 3, TennisGameStatus.DEUCE);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("deuce");
    }

    @Nested
    class PresentTennisGameRequestTest {

        @Test
        void ctor_illegal_args() {
            assertConstructorThrowsException(-1, 0, TennisGameStatus.STARTED);
            assertConstructorThrowsException(0, -1, TennisGameStatus.STARTED);
            assertConstructorThrowsException(0, 0, null);
        }

        private void assertConstructorThrowsException(int serverPoints, int receiverPoints, TennisGameStatus started) {
            assertThatThrownBy(() -> new PresentTennisGameRequest(serverPoints, receiverPoints, started))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    private static class TennisGameAnnouncingPresenter {
        public String present(final PresentTennisGameRequest request) {
            return null;
        }
    }
}
