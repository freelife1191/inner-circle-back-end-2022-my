package com.fastcampus.tennistdd.mskwon.service;

import com.fastcampus.tennistdd.mskwon.domain.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 테니스 게임 테스트를 위한 시뮬레이션 게임
 */
public class TennisGameSimulation {

    /**
     * TennisGame 테스트를 위해 승자 조건에 해당되는 승자가 나올때까지 가상 플레이
     */
    public static void play(Player server, Player receiver) {
        // 승자 조건에 해당하는 승자가 나올때까지 게임을 플레이
        while (TennisGameService.rulesVerification(server, receiver)) {
            updateRandomPlayerScore(server, receiver);
        }
    }

    /**
     * 랜덤 플레이어 득점 처리
     */
    private static void updateRandomPlayerScore(Player... players) {
        List<Player> playerList = new ArrayList<>(List.of(players));
        Collections.shuffle(playerList);
        Player player = playerList.stream().findFirst().orElseThrow(IllegalArgumentException::new);
        player.update(1);
    }
}
