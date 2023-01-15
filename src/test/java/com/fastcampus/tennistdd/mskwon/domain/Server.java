package com.fastcampus.tennistdd.mskwon.domain;

import com.fastcampus.tennistdd.mskwon.domain.enums.PlayerType;
import com.fastcampus.tennistdd.mskwon.repository.TennisGameRepository;

public class Server extends Player {

    public Server() {
        super.setType(PlayerType.SERVER);
        TennisGameRepository.savePlayer(this);
    }

    public static Server of() {
        return new Server();
    }

}
