package com.fastcampus.tennistdd.myeongseop.second;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("테니스 플레이어 테스트")
public class PlayerTest {

    @DisplayName("플레이어는 0~7점의 점수를 가진다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    void givenScore_whenConstructPlayer_thenOk(int point) {
        Player player = new Player(point);
        assertThat(player.getScore()).isNotNull();
        assertThat(player.getScore()).isEqualTo(new Score(point));
    }

    @DisplayName("플레이어는 0~7점 이외의 점수를 추가하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 8, 100, 23, 45})
    void givenInvalidPoint_whenConstructPlayer_thenException(int point) {
        assertThatThrownBy(() -> new Player(point)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("플레이어는 게임 포인트를 가지고 상대와 점수 차이가 2점 이상이면 승리한다.")
    @ParameterizedTest
    @CsvSource(value = {"4:2", "5:3", "6:4", "7:5"}, delimiter = ':')
    void givenIsGamePointAndDifferenceInPointGreaterThen2_whenIsGameBy_thenOk(int point, int otherPoint) {
        assertIsGameBy(point, otherPoint, true);
    }

    @DisplayName("플레이어는 게임 포인트를 가지고 상대와 점수 차이가 1점이면 승리하지 못한다.")
    @ParameterizedTest
    @CsvSource(value = {"4:3", "5:4", "6:5"}, delimiter = ':')
    void givenIsGamePointAndDifferenceInPointIs1_whenIsNotGameBy_thenOk(int point, int otherPoint) {
        assertIsGameBy(point, otherPoint, false);
    }

    @DisplayName("플레이어는 게임 포인트를 가지지 못하고 상대와 2점 이상 차이나면 승리하지 못한다.")
    @ParameterizedTest
    @CsvSource(value = {"2:0", "3:1", "3:2"}, delimiter = ':')
    void givenIsNotGamePointAndDifferenceInPointGreaterThen2_whenIsNotGameBy_thenOk(int point, int otherPoint) {
        assertIsGameBy(point, otherPoint, false);
    }

    @DisplayName("플레이어는 점수가 3점 이상이고 상대와 점수가 같다면 듀스 상황이다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6})
    void givenScoreGreaterThen3AndSameScoreWithOther_whenIsDeuce_thenOk(int point) {
        Player player = new Player(point);
        Player other = new Player(point);
        assertThat(player.isDeuce(other)).isEqualTo(true);
    }

    @DisplayName("듀스일 때 한 쪽이 점수를 득점하면 리드하는 플레이어에게 ad(advantage)이다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6})
    void givenIsDeuceAndGameByPlayer_whenAdvantage_thenOk(int point) {
        Player player = new Player(point);
        Player other = new Player(point);
        player.matchUp(other);
        assertThat(player.isAdvantage()).isTrue();
    }

    @DisplayName("듀스가 아닐 때 점수를 득점하면 ad(advantage)가 아니다.")
    @ParameterizedTest
    @CsvSource(value = {"3:4", "4:5", "6:5"}, delimiter = ':')
    void givenIsNotDeuceAndGameByPlayer_whenNotAdvantage_thenOk(int point, int otherPoint) {
        Player player = new Player(point);
        Player other = new Player(otherPoint);
        player.matchUp(other);
        assertThat(player.isAdvantage()).isFalse();
    }

    @DisplayName("6:6 인 경우, 플레이어가 득점하면 승리한다.")
    @Test
    void givenSamePointBy6AndGameByPlayer_whenWinnerIsTrue_thenOk() {
        Player player = new Player(6);
        Player other = new Player(6);
        player.matchUp(other);
        assertThat(player.isGameBy(other)).isTrue();
    }

    private void assertIsGameBy(int point, int otherPoint, boolean expected) {
        Player player = new Player(point);
        Player other = new Player(otherPoint);
        assertThat(player.isGameBy(other)).isEqualTo(expected);
    }

}
