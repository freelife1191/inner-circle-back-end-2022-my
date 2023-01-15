package com.fastcampus.tennistdd.myeongseop.second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("테니스 점수판 테스트")
public class ScoreBoardTest {

    @DisplayName("테니스 점수판은 서버와 리시버 점수를 나타낸다.")
    @Test
    void givenBoardScoreHasServerAndReceiver_whenConstruct_thenOk() {
        ScoreBoard scoreBoard = new ScoreBoard(new TennisGame(new Player(0), new Player(0)));
        assertThat(scoreBoard).isNotNull();
    }

    @DisplayName("절대 만들 수 없는 점수로 점수판을 만들 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"100:0", "3:6", "2:6", "7:7"}, delimiter = ':')
    void given7ScoreAndSameScore_whenConstruct_thenException(int serverPoint, int receiverPoint) {
        assertThatThrownBy(() -> new ScoreBoard(new TennisGame(new Player(serverPoint), new Player(receiverPoint))));
    }

    @DisplayName("이미 승리자가 결정되는 점수로 테니스 점수판을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"4:6", "7:5", "7:6"}, delimiter = ':')
    void givenInDecidedScore_whenConstruct_thenOk(int serverPoint, int receiverPoint) {
        ScoreBoard scoreBoard = new ScoreBoard(new TennisGame(new Player(serverPoint), new Player(receiverPoint)));
        assertThat(scoreBoard).isNotNull();
    }

    @DisplayName("0~3 점은 각각 love, fifteen, thirty, forty로 나타낸다.")
    @ParameterizedTest
    @CsvSource(
            value = {
                    "1:0:fifteen love", "2:1:thirty fifteen", "1:2:fifteen thirty",
                    "1:3:fifteen forty", "2:0:thirty love", "3:1:forty fifteen",
                    "3:2:forty thirty", "2:3:thirty forty", "3:0:forty love"
            },
            delimiter = ':'
    )
    void given0To3Score_whenViewScore_thenOk(int serverScore, int receiverScore, String expected) {
        ScoreBoard scoreBoard = new ScoreBoard(new TennisGame(new Player(serverScore), new Player(receiverScore)));
        String result = scoreBoard.viewScore();
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("0~2점이고 동점이면 all을 포함하여 나타낸다.(0:0 -> love all)")
    @ParameterizedTest
    @CsvSource(value = {"0:0:love all", "1:1:fifteen all", "1:2:fifteen thirty"}, delimiter = ':')
    void given0To2ScoreAndSameScore_whenViewScore_thenOk(int serverScore, int receiverScore, String expected) {
        ScoreBoard scoreBoard = new ScoreBoard(new TennisGame(new Player(serverScore), new Player(receiverScore)));
        String result = scoreBoard.viewScore();
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("3점 이상이고 동점이면 점수는 듀스이다.")
    @ParameterizedTest
    @CsvSource(value = {"3:3", "4:4", "5:5", "6:6"}, delimiter = ':')
    void givenGreaterThen3ScoreAndSameScore_whenDeuceScore_thenOk(int serverScore, int receiverScore) {
        ScoreBoard scoreBoard = new ScoreBoard(new TennisGame(new Player(serverScore), new Player(receiverScore)));
        String result = scoreBoard.viewScore();
        assertThat(result).isEqualTo("deuce");
    }

}
