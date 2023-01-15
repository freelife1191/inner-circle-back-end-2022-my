package com.fastcampus.tennistdd.mskwon.service;

import com.fastcampus.tennistdd.mskwon.domain.Player;
import com.fastcampus.tennistdd.mskwon.domain.enums.Advantage;
import com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType;
import com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameAwards;
import com.fastcampus.tennistdd.mskwon.repository.TennisGameRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.AD_IN;
import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.AD_OUT;
import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.DEUCE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.Advantage.NONE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType.RECEIVER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType.SERVER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameAwards.LOSER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameAwards.WINNER;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameRule.BASE_COMPARE_SCORE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameRule.BASE_DEUCE_SCORE;
import static com.fastcampus.tennistdd.mskwon.domain.enums.TennisGameRule.BASE_VICTORY_SCORE;

public class TennisGameService {

    /**
     * 테니스 게임 규칙 검증
     */
    public static boolean rulesVerification(Player server, Player receiver) {
        // 2점 상대방 보다 2점 이상이 더 많은 플레이어가 있는지 확인
        boolean compareScoreVerify = Math.abs(server.read() - receiver.read()) >= BASE_COMPARE_SCORE.getValue();
        // 총 4점인 플레이어가 있는지 확인
        boolean playerMaxScoreVerify = Math.max(server.read(), receiver.read()) >= BASE_VICTORY_SCORE.getValue();

        return !compareScoreVerify || !playerMaxScoreVerify;
    }

    /**
     * deuce 규칙 검증
     */
    public static boolean deuceVerification(Player server, Player receiver) {
        // 양 선수 최소 3점을 득점 (deuce 선행조건)
        boolean deucePrecedeVerify = server.read() >= BASE_DEUCE_SCORE.getValue() && receiver.read() >= BASE_DEUCE_SCORE.getValue();
        // 최소 3점을 득점하고 점수가 동점인 경우, 점수는 deuce
        return deucePrecedeVerify && server.read() == receiver.read();
    }

    /**
     * 경기 결과 승자와 패자를 선정
     */
    public static Map<TennisGameAwards, Player> awards(Player... players) {
        Map<TennisGameAwards, Player> result = new HashMap<>();
        boolean firstWinner = players[0].read() > players[1].read();
        result.put(WINNER, firstWinner ? players[0] : players[1]);
        result.put(LOSER, firstWinner ? players[1] : players[0]);
        return result;
    }

    /**
     * 0~3점 사이의 경기 스코어를 love, fifteen, thirty forty 와 함께 Display 해줌
     */
    public static String sumScoreDisplay(Player... players) {
        if (players.length != 2 || verifyPlayers(players)) {
            throw new IllegalArgumentException("You need two players, server and receiver!!");
        }
        // 항상 server가 먼저 나오도록 type으로 sorting
        List<Player> playerList = Arrays.stream(players).sorted(Comparator.comparing(Player::type)).toList();
        Player server = playerList.get(0);
        Player receiver = playerList.get(1);
        String serverName = server.display().getName();
        // 두개의 Display 정보가 동일하면 receiver Display 정보를 all로 변경
        String receiverName = serverName.equals(receiver.display().getName()) ? "all" : receiver.display().getName();
        StringJoiner scoreDisplayName = new StringJoiner(" ").add(serverName).add(receiverName);
        StringJoiner scoreDisplay = new StringJoiner(":").add(String.valueOf(server.read())).add(String.valueOf(receiver.read()));
        return scoreDisplayName + "(" + scoreDisplay + ")";
    }

    /**
     * Players 중에 SERVER, RECEIVER가 포함되었는 지 검증
     */
    private static boolean verifyPlayers(Player[] players) {
        return Arrays.stream(players).map(Player::type).toList().containsAll(List.of(SERVER, RECEIVER));
    }

    /**
     * 득점시 Advantage 셋팅
     */
    public static void setAdvantage(Player player) {
        // 저장전 Advantage 조회
        Advantage beforeAdvantage = TennisGameRepository.findAdvantage();

        // 메모리에 저장해둔 Player 데이터 조회
        Map<PlayerType, Player> players = TennisGameRepository.findAll();
        Player thisPlayer = players.get(player.type());
        Player oppnentPlayer = players.get(PlayerType.reverse(player.type()));
        // 득점 후 바로 deuce 검증
        boolean deuce = TennisGameService.deuceVerification(thisPlayer, oppnentPlayer);
        if (deuce) {
            // Advantage 상태 메모리DB에 저장
            TennisGameRepository.saveAdvantage(DEUCE);
        }

        boolean deuceConditon = beforeAdvantage == DEUCE && !deuce;
        boolean scoreConditon = Math.abs(thisPlayer.read() - oppnentPlayer.read()) <= 1;
        // 저장전 Player의 Advantage가 Deuce 이고 현재 Deuce가 아니고 이전 점수보다 높으면 Advantage 부여
        if (deuceConditon && scoreConditon) {
            savePlayerAdvantage(thisPlayer, oppnentPlayer);
        } else if (!scoreConditon) {
            TennisGameRepository.saveAdvantage(NONE);
        }
        // Advantage 계산 후 한번 더 저장
        TennisGameRepository.savePlayer(player);
    }

    /**
     * Advantage를 PlayerType에 따라 구분하여 저장
     */
    private static void savePlayerAdvantage(Player player1, Player player2) {
        Advantage saveAdvantage;
        if (player1.read() > player2.read()) {
            saveAdvantage = player1.type() == SERVER ? AD_IN : AD_OUT;
        } else {
            saveAdvantage = player1.type() == SERVER ? AD_OUT : AD_IN;
        }
        TennisGameRepository.saveAdvantage(saveAdvantage);
    }
}
