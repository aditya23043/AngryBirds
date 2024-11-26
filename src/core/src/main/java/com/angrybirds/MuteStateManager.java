package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MuteStateManager {

    private static boolean mute = true;
    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));;

    MuteStateManager() {
        music.setLooping(true);
    }

    public static void init() {
        if (!MuteStateManager.isMuted()) {
          MuteStateManager.getMusic().play();
        }
    }

    public static boolean isMuted() {
        return mute;
    }

    public static void setMuted(boolean value){
        mute = value;
        if (mute) {
            music.pause();
        }
        else {
            music.play();
        }
    }

    public static Music getMusic() {
        return music;
    }

}
