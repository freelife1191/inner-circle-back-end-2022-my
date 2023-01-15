package com.fastcampus.tennistdd.mskwon.repository;

import com.fastcampus.tennistdd.mskwon.domain.Player;
import com.fastcampus.tennistdd.mskwon.domain.enums.Advantage;
import com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType;

import java.util.HashMap;
import java.util.Map;

public class TennisGameRepository {
    private static final Map<PlayerType, Player> playersData = new HashMap<>();

    private static Advantage advantageData = Advantage.NONE;

    public static void savePlayer(Player player) {
        playersData.put(player.getType(), player);
    }

    public static Map<PlayerType, Player> findAll() {
        return playersData;
    }

    public static void saveAdvantage(Advantage advantage) {
        advantageData = advantage;
    }

    public static Advantage findAdvantage() {
        return advantageData;
    }
}
