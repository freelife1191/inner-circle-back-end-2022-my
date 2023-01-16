package com.fastcampus.tennistdd.practice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TennisGameAnnouncingPresenterTest {
    // system under test
    private final TennisGameAnnouncingPresenter sut = new TennisGameAnnouncingPresenter();

    @Test
    void case_deuce() {
        final PresentTennisGameRequest request = new PresentTennisGameRequest(3, 3, TennisGameStatus.DEUCE);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("deuce");
    }

    @Test
    void case_ad_in() {
        final PresentTennisGameRequest request = new PresentTennisGameRequest(4, 3, TennisGameStatus.ADVANTAGE_IN);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("ad-in");
    }

    @Test
    void case_ad_out() {
        final PresentTennisGameRequest request = new PresentTennisGameRequest(3, 4, TennisGameStatus.ADVANTAGE_OUT);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("ad-out");
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
            if (TennisGameStatus.ADVANTAGE_IN == request.getStatus()) return "ad-in";
            return "deuce";
        }
    }
}
