package com.angrybirds;

public class MuteStateManager {
    private static boolean mute = false;
    private static boolean mute = false;

    private MuteStateManager() { }

    public static boolean isMuted() {
        return mute;
    }

    public static void setMuted(boolean value){
        mute = value;
        if (mute) {
            AssetsManager.getMusic().pause();
        }
        else {
            AssetsManager.getMusic().play();
        }
    }


}
