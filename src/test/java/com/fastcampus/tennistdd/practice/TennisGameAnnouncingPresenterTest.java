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
        final var request = new PresentTennisGameRequest(3, 3, TennisGameStatus.DEUCE);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("deuce");
    }

    @Test
    void case_ad_in() {
        final var request = new PresentTennisGameRequest(4, 3, TennisGameStatus.ADVANTAGE_IN);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("ad-in");
    }

    @Test
    void case_ad_out() {
        final var request = new PresentTennisGameRequest(3, 4, TennisGameStatus.ADVANTAGE_OUT);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("ad-out");
    }

    @Test
    void case_finished_and_sever_win() {
        final var request = new PresentTennisGameRequest(4, 0, TennisGameStatus.FINISHED);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("server win!");
    }

    @Test
    void case_finished_and_receiver_win() {
        final var request = new PresentTennisGameRequest(0, 4, TennisGameStatus.FINISHED);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("receiver win!");
    }

    @Test
    void case_started() {
        assertStartedStatusResult(0, 0, "love all");
        assertStartedStatusResult(0, 1, "love fifteen");
        assertStartedStatusResult(1, 1, "fifteen all");
        assertStartedStatusResult(1, 2, "fifteen thirty");
        assertStartedStatusResult(3, 2, "forty thirty");
    }

    private void assertStartedStatusResult(final int serverPoints, final int receiverPoints, final String expected) {
        final var request = new PresentTennisGameRequest(serverPoints, receiverPoints, TennisGameStatus.STARTED);

        final String result = sut.present(request);

        assertThat(result).isEqualTo(expected);
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

}
