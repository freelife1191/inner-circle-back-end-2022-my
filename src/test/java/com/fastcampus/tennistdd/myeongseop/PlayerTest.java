package com.fastcampus.tennistdd.myeongseop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PlayerTest {

    @DisplayName("플레이어는 0~7 사이의 점수로 초기화해야 한다.")
    @Test
    void generatePlayer() {
        assertAll(
                () -> assertThat(new Player(0)).isNotNull(),
                () -> assertThat(new Player(7)).isNotNull()
        );
    }

    @DisplayName("플레이어는 0~7 사이의 점수로 초기화 하지 않으면 예외가 발생한다.")
    @Test
    void exceptionGeneratePlayerByWrongPoint() {
        assertAll(
                () -> assertThatThrownBy(() -> new Player(8), Player.INITIALIZE_POINT_EXCEPTION_MESSAGE)
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new Player(-1), Player.INITIALIZE_POINT_EXCEPTION_MESSAGE)
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new Player(100), Player.INITIALIZE_POINT_EXCEPTION_MESSAGE)
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("플레이어 점수 조회")
    @Test
    void getPointOfPlayer() {
        Player player = new Player(1);
        assertThat(player.getPoint()).isEqualTo(1);
    }

    @DisplayName("스코어 1 증가")
    @Test
    void increase() {
        Player player = new Player(0);
        player.increase();
        assertThat(player.getPoint()).isEqualTo(1);
    }

    @DisplayName("7점인 경우 스코어는 증가할 수 없다.")
    @Test
    void notIncreaseWhen7Point() {
        Player player = new Player(7);
        assertThatThrownBy(() -> player.increase()).isInstanceOf(AlreadyMaxPointException.class);
    }

    @DisplayName("플레이어 간 점수 차이 확인")
    @Test
    void difference() {
        Player player = new Player(1);
        Player other = new Player(3);
        assertThat(player.difference(other)).isEqualTo(2);
    }

}
