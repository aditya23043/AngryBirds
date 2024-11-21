package com.angrybirds;

import java.io.Serializable;

public class ConfData implements Serializable {

    private String name;
    public int score;

    ConfData(String name, int score) {
        this.name = name;
        this.score = score;
    }

}
