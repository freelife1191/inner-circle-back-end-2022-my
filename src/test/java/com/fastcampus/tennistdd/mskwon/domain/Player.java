package com.fastcampus.tennistdd.mskwon.domain;

import com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType;
import com.fastcampus.tennistdd.mskwon.domain.enums.ScoreDisplay;
import com.fastcampus.tennistdd.mskwon.repository.TennisGameRepository;
import com.fastcampus.tennistdd.mskwon.service.TennisGameService;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Player {

    private PlayerType type;

    private int score;

    /**
     * 0:love, 1:fifteen, 2:thirty, 3:forty
     */
    private ScoreDisplay display = ScoreDisplay.LOVE;

    public int read() {
        return this.score;
    }

    public ScoreDisplay display() {
        return this.display;
    }

    public PlayerType type() {
        return this.type;
    }

    /**
     * update시 입력받은 점수를 누적시키고 해당 누적점수에 맞는 scoreDisplay 정보를 확인하여 변경
     */
    public void update(int score) {
        this.score += score;
        this.display = ScoreDisplay.findByScore(this.score);

        // 득점 후 바로 메모리DB에 저장
        TennisGameRepository.savePlayer(this);

        // Advantege 셋팅
        TennisGameService.setAdvantage(this);
    }


}