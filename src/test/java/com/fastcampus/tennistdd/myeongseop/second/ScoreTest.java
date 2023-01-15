package com.fastcampus.tennistdd.myeongseop.second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("테니스 점수 테스트")
public class ScoreTest {

    @DisplayName("0~7 사이 값이 아닌 다른 점수를 입력하면 예외가 발생한다.")
    @Test
    void givenInvalidPoint_whenConstructScore_thenException() {
        assertThatThrownBy(() -> new Score(8)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Score(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Score(100)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("0~7 사이의 점수를 입력하면 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    void givenValidPoint_whenConstructScore_thenOk(int point) {
        assertThat(new Score(point)).isNotNull();
    }

    @DisplayName("점수가 7점인데 득점하면 예외가 발생한다.")
    @Test
    void given7Score_whenRun_thenException() {
        Score score = new Score(7);
        assertThatThrownBy(() -> score.run()).isInstanceOf(IllegalCallerException.class);
    }

    @DisplayName("0~6점인 경우 득점하면 점수가 증가한다.")
    @ParameterizedTest
    @CsvSource(value = {"0:1", "1:2", "2:3", "3:4", "4:5", "5:6", "6:7"}, delimiter = ':')
    void givenValidPoint_whenRun_thenOk(int point, int expected) {
        Score score = new Score(point);
        score.run();
        assertThat(score).isEqualTo(new Score(expected));
    }

    @DisplayName("두 점수의 차이는 항상 양수가 되어야 한다.")
    @ParameterizedTest
    @CsvSource(value = {"2:2:0", "3:4:1", "2:4:2", "5:3:2", "3:1:2"}, delimiter = ':')
    void givenDifferenceInScoreOfTwinPoints_thenPositiveNumber_thenOk(int point, int otherPoint, int expected) {
        Score score = new Score(point);
        Score other = new Score(otherPoint);
        int result = score.difference(other);
        assertThat(result).isEqualTo(expected);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @DisplayName("주어진 포인트보다 점수가 크거나 같다면 true")
    @ParameterizedTest
    @CsvSource(value = {"3:3", "4:3"}, delimiter = ':')
    void givenPoint_whenGreaterThen_thenOk(int point, int condition) {
        Score score = new Score(point);
        assertThat(score.isGreaterThen(condition)).isTrue();
    }
}
