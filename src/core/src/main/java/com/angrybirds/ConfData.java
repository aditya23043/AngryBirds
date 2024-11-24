package com.angrybirds;

import java.io.Serializable;

public class ConfData implements Serializable {

    private String username;
    public int score;

    ConfData(String name, int score) {
        this.username = name;
        this.score = score;
    }

}
