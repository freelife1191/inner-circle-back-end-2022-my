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

    @Test
    void case_finished_and_sever_win() {
        final PresentTennisGameRequest request = new PresentTennisGameRequest(4, 0, TennisGameStatus.FINISHED);

        final String result = sut.present(request);

        assertThat(result).isEqualTo("server win!");
    }

    @Test
    void case_finished_and_receiver_win() {
        final PresentTennisGameRequest request = new PresentTennisGameRequest(0, 4, TennisGameStatus.FINISHED);

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
        final PresentTennisGameRequest request = new PresentTennisGameRequest(serverPoints, receiverPoints, TennisGameStatus.STARTED);

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

    private static class TennisGameAnnouncingPresenter {
        public String present(final PresentTennisGameRequest request) {
            return switch (request.getStatus()) {
                case STARTED -> presentStarted(request.getServerPoints(), request.getReceiverPoints());
                case DEUCE -> "deuce";
                case ADVANTAGE_IN -> "ad-in";
                case ADVANTAGE_OUT -> "ad-out";
                case FINISHED -> presentFinished(request.getServerPoints(), request.getReceiverPoints());
            };
        }

        private String presentStarted(final int serverPoints, final int receiverPoints) {
            return serverPoints == receiverPoints ?
                "%s all".formatted(presentPoints(serverPoints)) :
                "%s %s".formatted(
                    presentPoints(serverPoints),
                    presentPoints(receiverPoints));
        }

        private String presentPoints(final int points) {
            return switch (points) {
                case 0 -> "love";
                case 1 -> "fifteen";
                case 2 -> "thirty";
                case 3 -> "forty";
                default -> throw new IllegalArgumentException();
            };
        }

        private String presentFinished(int serverPoints, int receiverPoints) {
            return serverPoints > receiverPoints ? "server win!" : "receiver win!";
        }
    }
}
