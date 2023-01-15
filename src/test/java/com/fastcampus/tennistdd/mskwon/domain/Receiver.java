package com.fastcampus.tennistdd.mskwon.domain;

import com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType;
import com.fastcampus.tennistdd.mskwon.repository.TennisGameRepository;

public class Receiver extends Player {

    public Receiver() {
        super.setType(PlayerType.RECEIVER);
        TennisGameRepository.savePlayer(this);
    }

    public static Receiver of() {
        return new Receiver();
    }

}